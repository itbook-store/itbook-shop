package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.nonmember;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class NonMemberSubscriptionOrderBeforePaymentCancel
    extends AbstractNonMemberOrderBeforePaymentCancel {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;

    public NonMemberSubscriptionOrderBeforePaymentCancel(
        OrderStatusHistoryService orderStatusHistoryService,
        OrderSubscriptionRepository orderSubscriptionRepository, OrderRepository orderRepository,
        OrderStatusService orderStatusService) {
        this.orderStatusHistoryService = orderStatusHistoryService;
        this.orderSubscriptionRepository = orderSubscriptionRepository;
        this.orderRepository = orderRepository;
        this.orderStatusService = orderStatusService;
    }

    @Override
    protected void processOrderBeforePaymentCancelWithoutMemberProcessing(Order order) {
        this.checkOrderStatus(order.getOrderNo());
        this.changeOrderStatusAboutOrderCancel(order);
    }

    protected void checkOrderStatus(Long orderNo) {
        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(orderNo);
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();
        if (!(orderStatusEnum.equals(OrderStatusEnum.PAYMENT_COMPLETE) ||
            orderStatusEnum.equals(OrderStatusEnum.WAIT_DELIVERY) ||
            orderStatusEnum.equals(OrderStatusEnum.DELIVERY_COMPLETED))) {
            throw new NotStatusOfOrderCancel();
        }
    }

    protected void changeOrderStatusAboutOrderCancel(Order order) {
        OrderSubscription orderSubscription =
            orderSubscriptionRepository.findByOrder_OrderNo(order.getOrderNo()).orElseThrow();

        Integer subscriptionPeriod = orderSubscription.getSubscriptionPeriod();
        List<Long> subScriptionOrderNoList = new ArrayList<>();

        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.REFUND_COMPLETED);

        for (long i = 1L; i < subscriptionPeriod; i++) {
            subScriptionOrderNoList.add(order.getOrderNo() + i);
        }

        List<Order> orderList = orderRepository.findOrdersByOrderNoIn(subScriptionOrderNoList);
        OrderStatus orderStatus =
            orderStatusService.findByOrderStatusEnum(OrderStatusEnum.REFUND_COMPLETED);

        for (Order subScriptionOrder : orderList) {
            orderStatusHistoryService.save(
                new OrderStatusHistory(subScriptionOrder, orderStatus, LocalDateTime.now()));
        }
    }
}
