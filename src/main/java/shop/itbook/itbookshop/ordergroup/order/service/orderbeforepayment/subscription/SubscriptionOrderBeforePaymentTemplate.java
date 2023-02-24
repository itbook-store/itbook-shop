package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.subscription;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForCouponIssueApply;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public abstract class SubscriptionOrderBeforePaymentTemplate implements OrderBeforePayment {

    private final OrderRepository orderRepository;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderProductService orderProductService;
    private final ProductService productService;

    @Override
    public OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess) {

        this.saveOrder(infoForPrePaymentProcess);
        this.saveOrderPerson(infoForPrePaymentProcess);
        OrderPaymentDto orderPaymentDto = this.calculateTotalAmount(infoForPrePaymentProcess);
        this.saveOrderSubscription(infoForPrePaymentProcess);

        return orderPaymentDto;
    }

    @Override
    public void saveOrder(InfoForPrePaymentProcess infoForPrePaymentProcess) {

        OrderAddRequestDto orderAddRequestDto = infoForPrePaymentProcess.getOrderAddRequestDto();

        Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
        order.setSelectedDeliveryDate(LocalDate.now().plusMonths(1).withDayOfMonth(1));
        orderRepository.save(order);

        infoForPrePaymentProcess.setOrder(order);

        OrderSubscription orderSubscription = OrderSubscription.builder().order(order)
            .subscriptionStartDate(LocalDate.now().plusMonths(1).withDayOfMonth(1))
            .sequence(1).subscriptionPeriod(orderAddRequestDto.getSubscriptionPeriod())
            .build();

        orderSubscriptionRepository.save(orderSubscription);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order,
            OrderStatusEnum.WAITING_FOR_PAYMENT);
    }

    private void saveOrderSubscription(InfoForPrePaymentProcess infoForPrePaymentProcess) {

        OrderAddRequestDto orderAddRequestDto = infoForPrePaymentProcess.getOrderAddRequestDto();
        Integer subscriptionPeriod =
            infoForPrePaymentProcess.getOrderAddRequestDto().getSubscriptionPeriod();

        ProductDetailsDto productDetailsDto =
            orderAddRequestDto.getProductDetailsDtoList().get(0);
        Product product = productService.findProductEntity(
            productDetailsDto.getProductNo());

        Order order = infoForPrePaymentProcess.getOrder();

        for (int i = 2; i <= subscriptionPeriod; i++) {

            Order orderChild = OrderTransfer.addDtoToEntity(orderAddRequestDto);
            orderChild.setSelectedDeliveryDate(LocalDate.now().plusMonths(i).withDayOfMonth(1));
            orderRepository.save(orderChild);

            OrderSubscription orderSubscriptionChild =
                OrderSubscription
                    .builder()
                    .order(orderChild)
                    .subscriptionStartDate(LocalDate.now().plusMonths(1).withDayOfMonth(1))
                    .sequence(i)
                    .subscriptionPeriod(orderAddRequestDto.getSubscriptionPeriod())
                    .build();

            orderSubscriptionRepository.save(orderSubscriptionChild);
            orderStatusHistoryService.addOrderStatusHistory(orderChild,
                OrderStatusEnum.WAITING_FOR_PAYMENT);

            orderProductService.addOrderProduct(orderChild, product, 0,
                0L);
        }

    }

    @Override
    public abstract void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    @Override
    public abstract OrderPaymentDto calculateTotalAmount(
        InfoForPrePaymentProcess infoForPrePaymentProcess);
}
