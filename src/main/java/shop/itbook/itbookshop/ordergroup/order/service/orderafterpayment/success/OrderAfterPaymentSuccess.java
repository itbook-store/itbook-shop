package shop.itbook.itbookshop.ordergroup.order.service.orderafterpayment.success;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderAfterPaymentSuccess {
    Order success(Order order);

    void changeOrderStatus(Order order);
}
