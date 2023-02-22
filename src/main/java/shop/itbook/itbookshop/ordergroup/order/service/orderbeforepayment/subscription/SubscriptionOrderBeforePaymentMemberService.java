package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.CouponApplyDto;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForCouponIssueApply;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.ProductsTotalAmount;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.AmountException;
import shop.itbook.itbookshop.ordergroup.order.exception.CanNotSaveRedisException;
import shop.itbook.itbookshop.ordergroup.order.exception.MismatchCategoryNoWhenCouponApplyException;
import shop.itbook.itbookshop.ordergroup.order.exception.MismatchProductNoWhenCouponApplyException;
import shop.itbook.itbookshop.ordergroup.order.exception.NotOrderTotalCouponException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.util.AmountCalculationBeforePaymentUtil;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderBeforePaymentMemberService
    extends SubscriptionOrderBeforePaymentTemplate {

    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public static final long BASE_AMOUNT_FOR_DELIVERY_FEE_CALC = 20000L;
    public static final long BASE_DELIVERY_FEE = 3000L;
    private final MemberService memberService;
    private final OrderMemberRepository orderMemberRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderProductService orderProductService;


    private final CategoryCouponRepository categoryCouponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCouponRepository productCouponRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final OrderTotalCouponRepository orderTotalCouponRepository;

    public SubscriptionOrderBeforePaymentMemberService(
        OrderRepository orderRepository,
        OrderSubscriptionRepository orderSubscriptionRepository,
        OrderStatusHistoryService orderStatusHistoryService, MemberService memberService,
        OrderMemberRepository orderMemberRepository, RedisTemplate redisTemplate,
        ObjectMapper objectMapper, ProductService productService,
        OrderProductService orderProductService,
        CategoryCouponRepository categoryCouponRepository,
        ProductCategoryRepository productCategoryRepository,
        ProductCouponRepository productCouponRepository,
        CouponIssueRepository couponIssueRepository,
        OrderTotalCouponRepository orderTotalCouponRepository) {

        super(orderRepository, orderSubscriptionRepository, orderStatusHistoryService);

        this.memberService = memberService;
        this.orderMemberRepository = orderMemberRepository;
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.productService = productService;
        this.orderProductService = orderProductService;

        this.categoryCouponRepository = categoryCouponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productCouponRepository = productCouponRepository;
        this.couponIssueRepository = couponIssueRepository;
        this.orderTotalCouponRepository = orderTotalCouponRepository;
    }


    @Transactional
    @Override
    public void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        Member member = memberService.findMemberByMemberNo(infoForPrePaymentProcess.getMemberNo());
        OrderMember orderMember = new OrderMember(infoForPrePaymentProcess.getOrder(), member);
        orderMemberRepository.save(orderMember);
    }

    @Override
    public OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        StringBuilder stringBuilder = new StringBuilder();

        OrderAddRequestDto orderAddRequestDto = infoForPrePaymentProcess.getOrderAddRequestDto();
        Order order = infoForPrePaymentProcess.getOrder();

        long amount = 0L;
        List<ProductDetailsDto> productDetailsDtoList =
            orderAddRequestDto.getProductDetailsDtoList();


        ProductsTotalAmount productsTotalAmount =
            this.calculateAmountAboutOrderProductCoupon(order, stringBuilder, amount,
                productDetailsDtoList, orderAddRequestDto.getSubscriptionPeriod());

        amount = this.calculateAmountAboutOrderTotalAmountCoupon(orderAddRequestDto,
            productsTotalAmount.getSellingAmount(), productsTotalAmount.getCouponAppliedAmount(),
            order.getOrderNo());

        amount += order.getDeliveryFee();
        amount = doProcessPointDecreaseAndGetAmount(orderAddRequestDto, order, amount);


        // toss 정책 상 100원 이하 결제 막기.
        if (amount <= 100) {
            throw new AmountException();
        }

        order.setAmount(amount);
        orderRepository.save(order);

        return OrderPaymentDto.builder().orderNo(order.getOrderNo())
            .orderId(this.createOrderUUID(order)).orderName(stringBuilder.toString()).amount(amount)
            .successUrl(ORIGIN_URL + "orders/success/" + order.getOrderNo())
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo()).build();

    }

    private long doProcessPointDecreaseAndGetAmount(OrderAddRequestDto orderAddRequestDto,
                                                    Order order, long amount) {

        Long decreasePoint = orderAddRequestDto.getDecreasePoint();
        if (Objects.equals(decreasePoint, 0L)) {
            return amount;
        }
        if (amount < decreasePoint) {
            decreasePoint = amount;
        }

        amount = this.calculateAmountAboutPoint(amount, decreasePoint);
        order.setDecreasePoint(decreasePoint);
        return amount;
    }

    private ProductsTotalAmount calculateAmountAboutOrderProductCoupon(Order order,
                                                                       StringBuilder stringBuilder,
                                                                       long amount,
                                                                       List<ProductDetailsDto> productDetailsDtoList,
                                                                       Integer subscriptionPeriod) {

        List<InfoForCouponIssueApply> infoForCouponIssueApplyList = new ArrayList<>();


        // TODO jun : 상품 번호들로 한번에 가져오는 로직추가
//        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {
//            productService.findProductEntityListByProductNoList();
//        }

        long amountForDeliveryFeeCalc = 0L;
        long sumTotalPriceOfSameProducts = 0L;

        List<Long> productNoList =
            productDetailsDtoList.stream().map(ProductDetailsDto::getProductNo)
                .collect(Collectors.toList());
        List<Product> productEntityList = productService.findProductEntityList(productNoList);

        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {
            Product product = productEntityList.stream()
                .filter(a -> (a.getProductNo() == productDetailsDto.getProductNo())).findFirst()
                .get();

            long sellingPrice = (product.getFixedPrice() -
                getDiscountedPrice(product.getFixedPrice(), product.getDiscountPercent()));
            amountForDeliveryFeeCalc += sellingPrice * subscriptionPeriod;

            long totalPriceOfSameProducts = sellingPrice * subscriptionPeriod;
            sumTotalPriceOfSameProducts += totalPriceOfSameProducts;
            amount += totalPriceOfSameProducts;

            if (stringBuilder.length() == 0) {
                stringBuilder.append(product.getName());
            }


            Coupon coupon =
                this.getCoupon(productDetailsDto.getCouponIssueNo(), totalPriceOfSameProducts);
            if (Objects.isNull(coupon)) {
                orderProductService.addOrderProduct(order, product, subscriptionPeriod,
                    totalPriceOfSameProducts);
                this.increasePointAboutOrderProduct(order, product, totalPriceOfSameProducts,
                    totalPriceOfSameProducts);
                continue;
            }

            checkMismatchAboutRequestedProductAndCoupon(product, coupon);

            Long totalPriceOfSameProductsWithCouponApplied =
                AmountCalculationBeforePaymentUtil.getTotalPriceWithCouponApplied(coupon,
                    totalPriceOfSameProducts, sellingPrice);
            long discountedPrice =
                totalPriceOfSameProducts - totalPriceOfSameProductsWithCouponApplied;

            amount = AmountCalculationBeforePaymentUtil.subAmountToDiscountedPriceAndNegativeCheck(
                amount, discountedPrice);

            OrderProduct orderProduct =
                orderProductService.addOrderProduct(order, product, subscriptionPeriod,
                    totalPriceOfSameProductsWithCouponApplied);

            InfoForCouponIssueApply infoCouponIssueApply =
                new InfoForCouponIssueApply(productDetailsDto.getCouponIssueNo(),
                    orderProduct.getOrderProductNo());
            infoForCouponIssueApplyList.add(infoCouponIssueApply);

            this.increasePointAboutOrderProduct(order, product, totalPriceOfSameProducts,
                totalPriceOfSameProductsWithCouponApplied);
        }


        if (!infoForCouponIssueApplyList.isEmpty()) {
            try {
                redisTemplate.opsForHash()
                    .put("productAndCategoryCouponApplyDto", String.valueOf(order.getOrderNo()),
                        objectMapper.writeValueAsString(
                            new CouponApplyDto(infoForCouponIssueApplyList)));

            } catch (JsonProcessingException e) {
                throw new CanNotSaveRedisException();
            }
        }

        if (productDetailsDtoList.size() > 1) {
            stringBuilder.append(" 외 ").append(productDetailsDtoList.size() - 1).append("건");
        }

        if (amountForDeliveryFeeCalc >= BASE_AMOUNT_FOR_DELIVERY_FEE_CALC) {
            order.setDeliveryFee(0L);
        } else {
            order.setDeliveryFee(BASE_DELIVERY_FEE);
        }

        return new ProductsTotalAmount(sumTotalPriceOfSameProducts, amount);
    }

//    private void calculateTotalAmount(List<Product> productEntityList) {
//        productEntityList.stream().mapToLong(product ->
//            product.getFixedPrice() -
//                getDiscountedPrice(product.getFixedPrice(), product.getDiscountPercent());
//        }).sum();
//    }

    private static long getDiscountedPrice(Long priceToApply, Double discountPercent) {
        return (long) (priceToApply * (discountPercent / 100));
    }

    private void checkMismatchAboutRequestedProductAndCoupon(Product product, Coupon coupon) {


        Optional<CategoryCoupon> optionalCategoryCoupon =
            categoryCouponRepository.findById(coupon.getCouponNo());

        if (optionalCategoryCoupon.isPresent()) {
            Integer categoryNoAboutCoupon =
                optionalCategoryCoupon.get().getCategory().getCategoryNo();
            Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(
                new ProductCategory.Pk(product.getProductNo(), categoryNoAboutCoupon));
            if (optionalProductCategory.isEmpty()) {
                throw new MismatchCategoryNoWhenCouponApplyException();
            }
        } else {
            Optional<ProductCoupon> optionalProductCoupon =
                productCouponRepository.findById(coupon.getCouponNo());
            if (optionalProductCoupon.isPresent()) {
                Long productNoAboutCoupon = optionalProductCoupon.get().getProduct().getProductNo();
                if (!Objects.equals(product.getProductNo(), productNoAboutCoupon)) {
                    throw new MismatchProductNoWhenCouponApplyException();
                }
            }
        }
    }

    @SuppressWarnings("java:S5411")
    private void increasePointAboutOrderProduct(Order order, Product product,
                                                long totalPriceAboutSellingPrice,
                                                long totalPriceAboutSellingPriceWithCouponApplied) {

        if (product.getIsPointApplying()) {
            Long increasePoint = order.getIncreasePoint();

            if (product.getIsPointApplyingBasedSellingPrice()) {

                Long resultPoint = increasePoint + getDiscountedPrice(totalPriceAboutSellingPrice,
                    product.getIncreasePointPercent());
                order.setIncreasePoint(resultPoint);
            } else {

                Long resultPoint = increasePoint +
                    getDiscountedPrice(totalPriceAboutSellingPriceWithCouponApplied,
                        product.getIncreasePointPercent());
                order.setIncreasePoint(resultPoint);
            }
        }
    }

    private Coupon getCoupon(Long couponIssueNo, Long basePriceToCompareAboutStandardAmount) {

        if (Objects.isNull(couponIssueNo)) {
            return null;
        }


        Optional<CouponIssue> optionalCouponIssue = couponIssueRepository.findById(couponIssueNo);

        if (optionalCouponIssue.isEmpty()) {
            return null;
        }

        CouponIssue couponIssue = optionalCouponIssue.get();
        return AmountCalculationBeforePaymentUtil.getAvailableCoupon(couponIssue,
            basePriceToCompareAboutStandardAmount);
    }

    private long calculateAmountAboutOrderTotalAmountCoupon(OrderAddRequestDto orderAddRequestDto,
                                                            long sellingAmount,
                                                            long couponAppliedAmount,
                                                            Long orderNo) {
        Coupon coupon = this.getCoupon(orderAddRequestDto.getOrderTotalCouponNo(), sellingAmount);
        if (Objects.isNull(coupon)) {
            return couponAppliedAmount;
        }

        checkMismatchAboutTypeOfOrderTotalCoupon(coupon);

        try {
            redisTemplate.opsForHash().put("orderTotalCouponApplyDto", String.valueOf(orderNo),
                objectMapper.writeValueAsString(new CouponApplyDto(List.of(
                    new InfoForCouponIssueApply(orderAddRequestDto.getOrderTotalCouponNo(),
                        null)))));

        } catch (JsonProcessingException e) {
            throw new CanNotSaveRedisException();
        }

        return AmountCalculationBeforePaymentUtil.getTotalPriceWithCouponApplied(coupon,
            couponAppliedAmount,
            couponAppliedAmount);
    }

    private void checkMismatchAboutTypeOfOrderTotalCoupon(Coupon coupon) {
        Optional<OrderTotalCoupon> optionalOrderTotalCoupon =
            orderTotalCouponRepository.findById(coupon.getCouponNo());
        if (optionalOrderTotalCoupon.isEmpty()) {
            throw new NotOrderTotalCouponException();
        }
    }

    private long calculateAmountAboutPoint(long amount, long pointToBeDiscounted) {

        return AmountCalculationBeforePaymentUtil.subAmountToDiscountedPriceAndNegativeCheck(amount,
            pointToBeDiscounted);
    }

    private String createOrderUUID(Order order) {

        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        return UUID.fromString(randomUuidString).toString();
    }
}
