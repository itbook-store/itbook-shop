package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.general;

import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class GeneralOrderBeforePaymentCancelTemplate implements OrderBeforePaymentCancel {

    private final OrderStatusHistoryService orderStatusHistoryService;

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
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.REFUND_COMPLETED);
    }

    protected abstract void startUsageProcessing(Order order);
}
