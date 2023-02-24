package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.subscription;

import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class SubscriptionOrderAfterPaymentSuccessTemplate implements
    OrderAfterPaymentSuccess {

    private final OrderStatusHistoryService orderStatusHistoryService;

    protected void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);
    }
}
