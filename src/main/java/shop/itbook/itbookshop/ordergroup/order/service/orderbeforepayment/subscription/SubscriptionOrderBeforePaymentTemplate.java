package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.subscription;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class SubscriptionOrderBeforePaymentTemplate implements OrderBeforePayment {

    private final OrderRepository orderRepository;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;

    @Override
    public OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess) {

        this.saveOrderAndSub(infoForPrePaymentProcess);
        this.saveOrderPerson(infoForPrePaymentProcess);
        this.saveOrderProduct();
        OrderPaymentDto orderPaymentDto = this.calculateTotalAmount(infoForPrePaymentProcess);
        this.saveOrderAndSub(infoForPrePaymentProcess);

        return orderPaymentDto;
    }

    @Override
    public void saveOrderAndSub(InfoForPrePaymentProcess infoForPrePaymentProcess) {

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

    @Override
    public void saveOrderProduct() {

    }

    @Override
    public abstract void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    @Override
    public abstract OrderPaymentDto calculateTotalAmount(
        InfoForPrePaymentProcess infoForPrePaymentProcess);
}
