package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.subscription;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.service.OrderSubscriptionService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class SubscriptionOrderBeforePaymentCancelTemplate
    implements OrderBeforePaymentCancel {
    private final OrderStatusHistoryService orderStatusHistoryService;

    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;

    @Override
    public void cancel(Order order) {

        checkOrderStatus(order.getOrderNo());

        changeOrderStatusAboutOrderCancel(order);
        startUsageProcessing(order);
    }

    @Override
    public void checkOrderStatus(Long orderNo) {
        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(orderNo);
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();
        if (!(orderStatusEnum.equals(OrderStatusEnum.PAYMENT_COMPLETE) ||
            orderStatusEnum.equals(OrderStatusEnum.WAIT_DELIVERY) ||
            orderStatusEnum.equals(OrderStatusEnum.DELIVERY_COMPLETED))) {
            throw new NotStatusOfOrderCancel();
        }
    }

    @Override
    public void changeOrderStatusAboutOrderCancel(Order order) {
        OrderSubscription orderSubscription =
            orderSubscriptionRepository.findByOrder_OrderNo(order.getOrderNo()).orElseThrow();

        Integer subscriptionPeriod = orderSubscription.getSubscriptionPeriod();
        List<Long> subScriptionOrderNoList = new ArrayList<>();
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

    protected abstract void startUsageProcessing(Order order);
}
