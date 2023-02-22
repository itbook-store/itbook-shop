package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.subscription;

import static shop.itbook.itbookshop.ordergroup.order.service.impl.OrderServiceImpl.BASE_AMOUNT_FOR_DELIVERY_FEE_CALC;
import static shop.itbook.itbookshop.ordergroup.order.service.impl.OrderServiceImpl.BASE_DELIVERY_FEE;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.ProductsTotalAmount;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.AmountException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderBeforePaymentNonMemberService
    extends SubscriptionOrderBeforePaymentTemplate {

    private final OrderNonMemberRepository orderNonMemberRepository;
    private final OrderRepository orderRepository;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final ProductService productService;
    private final OrderProductService orderProductService;

    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    public SubscriptionOrderBeforePaymentNonMemberService(
        OrderNonMemberRepository orderNonMemberRepository,
        OrderRepository orderRepository,
        OrderSubscriptionRepository orderSubscriptionRepository,
        OrderStatusHistoryService orderStatusHistoryService,
        ProductService productService,
        OrderProductService orderProductService) {

        super(orderRepository, orderSubscriptionRepository, orderStatusHistoryService,
            orderProductService, productService);
        this.orderNonMemberRepository = orderNonMemberRepository;
        this.orderRepository = orderRepository;
        this.orderSubscriptionRepository = orderSubscriptionRepository;
        this.orderStatusHistoryService = orderStatusHistoryService;
        this.productService = productService;
        this.orderProductService = orderProductService;
    }

    @Override
    @Transactional
    public void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        OrderNonMember orderNonMember =
            new OrderNonMember(infoForPrePaymentProcess.getOrder(), UUID.randomUUID().toString());
        orderNonMemberRepository.save(orderNonMember);
    }

    @Override
    @Transactional
    public OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess) {

        OrderAddRequestDto orderAddRequestDto = infoForPrePaymentProcess.getOrderAddRequestDto();
        Order order = infoForPrePaymentProcess.getOrder();

        StringBuilder stringBuilder = new StringBuilder();

        long amount = 0L;
        List<ProductDetailsDto> productDetailsDtoList =
            orderAddRequestDto.getProductDetailsDtoList();

        Integer subscriptionPeriod = orderAddRequestDto.getSubscriptionPeriod();

        ProductsTotalAmount productsTotalAmount =
            this.calculateAmountAboutOrderProductCoupon(order, stringBuilder, amount,
                productDetailsDtoList, subscriptionPeriod);

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
            .successUrl(ORIGIN_URL + "orders/success/" + order.getOrderNo())
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo()).build();
    }

    private ProductsTotalAmount calculateAmountAboutOrderProductCoupon(Order order,
                                                                       StringBuilder stringBuilder,
                                                                       long amount,
                                                                       List<ProductDetailsDto> productDetailsDtoList,
                                                                       Integer subscriptionPeriod) {

        // TODO jun : 상품 번호들로 한번에 가져오는 로직추가
//        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {
//            productService.findProductEntityListByProductNoList();
//        }
        long amountForDeliveryFeeCalc = 0L;
        long sumTotalPriceOfSameProducts = 0L;
        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {
            // TODO jun : for문 주문구독일때는 없애야함
            Product product = productService.findProductEntity(productDetailsDto.getProductNo());

            long sellingPrice = (product.getFixedPrice() -
                getDiscountedPrice(product.getFixedPrice(), product.getDiscountPercent()));
            amountForDeliveryFeeCalc += sellingPrice * subscriptionPeriod;

            long totalPriceOfSameProducts = sellingPrice * subscriptionPeriod;
            sumTotalPriceOfSameProducts += totalPriceOfSameProducts;
            amount += totalPriceOfSameProducts;

            if (stringBuilder.length() == 0) {
                stringBuilder.append(product.getName());
            }

            orderProductService.addOrderProduct(order, product, subscriptionPeriod,
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

    private static long getDiscountedPrice(Long priceToApply, Double discountPercent) {
        return (long) (priceToApply * (discountPercent / 100));
    }

    private String createOrderUUID(Order order) {

        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        return UUID.fromString(randomUuidString).toString();
    }
}
