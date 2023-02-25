package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.nonmember;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.ProductsTotalAmount;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.AmountException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderAfterPaymentSuccessFactoryEnum;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractNonMemberOrderBeforePayment implements OrderBeforePayment {

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
    private final OrderNonMemberRepository orderNonMemberRepository;

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public static final long BASE_AMOUNT_FOR_DELIVERY_FEE_CALC = 20000L;
    public static final long BASE_DELIVERY_FEE = 3000L;

    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    @Override
    public OrderPaymentDto processOrderBeforePayment(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {

        this.saveOrder(infoForProcessOrderBeforePayment);

        this.saveOrderPerson(infoForProcessOrderBeforePayment);

        OrderPaymentDto orderPaymentDto = this.calculateTotalAmount(
            infoForProcessOrderBeforePayment);

        return orderPaymentDto;
    }

    protected abstract void saveOrder(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment);

    @Transactional
    public void saveOrderPerson(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {
        OrderNonMember orderNonMember =
            new OrderNonMember(infoForProcessOrderBeforePayment.getOrder(),
                UUID.randomUUID().toString());
        orderNonMemberRepository.save(orderNonMember);
    }


    @Transactional
    public OrderPaymentDto calculateTotalAmount(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {

        OrderAddRequestDto orderAddRequestDto =
            infoForProcessOrderBeforePayment.getOrderAddRequestDto();
        Order order = infoForProcessOrderBeforePayment.getOrder();

        StringBuilder stringBuilder = new StringBuilder();

        long amount = 0L;
        List<ProductDetailsDto> productDetailsDtoList =
            orderAddRequestDto.getProductDetailsDtoList();

        ProductsTotalAmount productsTotalAmount =
            this.calculateAmountAboutOrderProduct(order, stringBuilder, amount,
                productDetailsDtoList);
        amount = productsTotalAmount.getSellingAmount();
        amount += order.getDeliveryFee();

        // toss 정책 상 100원 이하 결제 막기.
        if (amount <= 100) {
            throw new AmountException();
        }

        order.setAmount(amount);
        orderRepository.save(order);

        return OrderPaymentDto.builder().orderNo(order.getOrderNo())
            .orderId(this.createOrderUUID(order)).orderName(stringBuilder.toString()).amount(amount)
            .successUrl(String.format(ORIGIN_URL + "orders/success/%d?orderType=%s",
                infoForProcessOrderBeforePayment.getOrder().getOrderNo(),
                OrderAfterPaymentSuccessFactoryEnum.일반비회원주문.name()))
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo()).build();
    }

    private ProductsTotalAmount calculateAmountAboutOrderProduct(Order order,
                                                                 StringBuilder stringBuilder,
                                                                 long amount,
                                                                 List<ProductDetailsDto> productDetailsDtoList) {

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

            orderProductService.addOrderProduct(order, product, productCnt,
                totalPriceOfSameProducts);
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

    private String createOrderUUID(Order order) {

        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        return UUID.fromString(randomUuidString).toString();
    }

    private static long getDiscountedPrice(Long priceToApply, Double discountPercent) {
        return (long) (priceToApply * (discountPercent / 100));
    }


}
