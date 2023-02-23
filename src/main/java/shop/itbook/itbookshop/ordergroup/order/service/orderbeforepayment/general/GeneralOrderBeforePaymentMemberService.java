package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
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
import shop.itbook.itbookshop.ordergroup.order.exception.ProductStockIsZeroException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderAfterPaymentSuccessFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.util.AmountCalculationBeforePaymentUtil;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderBeforePaymentMemberService extends GeneralOrderBeforePaymentTemplate {

    private final MemberService memberService;
    private final ProductService productService;
    private final OrderProductService orderProductService;

    private final OrderStatusHistoryService orderStatusHistoryService;


    private final OrderMemberRepository orderMemberRepository;
    private final OrderRepository orderRepository;
    private final CategoryCouponRepository categoryCouponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCouponRepository productCouponRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final OrderTotalCouponRepository orderTotalCouponRepository;


    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public static final long BASE_AMOUNT_FOR_DELIVERY_FEE_CALC = 20000L;
    public static final long BASE_DELIVERY_FEE = 3000L;

    public GeneralOrderBeforePaymentMemberService(MemberService memberService,
                                                  ProductService productService,
                                                  OrderProductService orderProductService,
                                                  OrderStatusHistoryService orderStatusHistoryService,
                                                  OrderMemberRepository orderMemberRepository,
                                                  OrderRepository orderRepository,
                                                  CategoryCouponRepository categoryCouponRepository,
                                                  ProductCategoryRepository productCategoryRepository,
                                                  ProductCouponRepository productCouponRepository,
                                                  CouponIssueRepository couponIssueRepository,
                                                  OrderTotalCouponRepository orderTotalCouponRepository,
                                                  RedisTemplate redisTemplate,
                                                  ObjectMapper objectMapper) {

        super(orderRepository, orderStatusHistoryService);
        this.memberService = memberService;
        this.productService = productService;
        this.orderStatusHistoryService = orderStatusHistoryService;
        this.orderProductService = orderProductService;
        this.orderMemberRepository = orderMemberRepository;
        this.orderRepository = orderRepository;
        this.categoryCouponRepository = categoryCouponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productCouponRepository = productCouponRepository;
        this.couponIssueRepository = couponIssueRepository;
        this.orderTotalCouponRepository = orderTotalCouponRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        Member member = memberService.findMemberByMemberNo(infoForPrePaymentProcess.getMemberNo());
        OrderMember orderMember = new OrderMember(infoForPrePaymentProcess.getOrder(), member);
        orderMemberRepository.save(orderMember);
    }

    @Override
    public OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        StringBuilder stringBuilder = new StringBuilder();

        long amount = 0L;
        List<ProductDetailsDto> productDetailsDtoList =
            infoForPrePaymentProcess.getOrderAddRequestDto().getProductDetailsDtoList();


        ProductsTotalAmount productsTotalAmount =
            this.calculateAmountAboutOrderProductCoupon(infoForPrePaymentProcess.getOrder(),
                stringBuilder, amount,
                productDetailsDtoList);

        amount = this.calculateAmountAboutOrderTotalAmountCoupon(
            infoForPrePaymentProcess.getOrderAddRequestDto(),
            productsTotalAmount.getSellingAmount(), productsTotalAmount.getCouponAppliedAmount(),
            infoForPrePaymentProcess.getOrder().getOrderNo());

        amount += infoForPrePaymentProcess.getOrder().getDeliveryFee();

        amount =
            doProcessPointDecreaseAndGetAmount(infoForPrePaymentProcess.getOrderAddRequestDto(),
                infoForPrePaymentProcess.getOrder(), amount);

        // toss 정책 상 100원 이하 결제 막기.
        if (amount <= 100) {
            throw new AmountException();
        }

        infoForPrePaymentProcess.getOrder().setAmount(amount);
        orderRepository.save(infoForPrePaymentProcess.getOrder());

        return OrderPaymentDto.builder().orderNo(infoForPrePaymentProcess.getOrder().getOrderNo())
            .orderId(this.createOrderUUID(infoForPrePaymentProcess.getOrder()))
            .orderName(stringBuilder.toString()).amount(amount)
            .successUrl(String.format(ORIGIN_URL + "orders/success/%d?orderType=%s",
                infoForPrePaymentProcess.getOrder().getOrderNo(),
                OrderAfterPaymentSuccessFactoryEnum.일반회원주문.name()))
            .failUrl(ORIGIN_URL + "orders/fail" + infoForPrePaymentProcess.getOrder().getOrderNo())
            .build();

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
                                                                       List<ProductDetailsDto> productDetailsDtoList) {

        List<InfoForCouponIssueApply> infoForCouponIssueApplyList = new ArrayList<>();

        // TODO jun : 상품 번호들로 한번에 가져오는 로직추가
//        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {
//            productService.findProductEntityListByProductNoList();
//        }
        long amountForDeliveryFeeCalc = 0L;
        long sumTotalPriceOfSameProducts = 0L;
        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {

            Product product = productService.findProductEntity(productDetailsDto.getProductNo());
            Integer productCnt = productDetailsDto.getProductCnt();

            long sellingPrice = (product.getFixedPrice() -
                getDiscountedPrice(product.getFixedPrice(), product.getDiscountPercent()));
            amountForDeliveryFeeCalc += sellingPrice * productCnt;

            long totalPriceOfSameProducts = sellingPrice * productCnt;
            sumTotalPriceOfSameProducts += totalPriceOfSameProducts;
            amount += totalPriceOfSameProducts;

            if (stringBuilder.length() == 0) {
                stringBuilder.append(product.getName());
            }

            Coupon coupon =
                this.getCoupon(productDetailsDto.getCouponIssueNo(), totalPriceOfSameProducts);
            if (Objects.isNull(coupon)) {
                orderProductService.addOrderProduct(order, product, productCnt,
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

            amount = AmountCalculationBeforePaymentUtil.getDiscountedAmountAfterNegativeCheck(
                amount, discountedPrice);

            OrderProduct orderProduct =
                orderProductService.addOrderProduct(order, product, productCnt,
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

        return AmountCalculationBeforePaymentUtil.getDiscountedAmountAfterNegativeCheck(amount,
            pointToBeDiscounted);
    }

    private String createOrderUUID(Order order) {

        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        return UUID.fromString(randomUuidString).toString();
    }
}
