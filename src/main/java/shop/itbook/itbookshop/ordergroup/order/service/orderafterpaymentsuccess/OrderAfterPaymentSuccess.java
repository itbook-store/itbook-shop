package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderAfterPaymentSuccess {
    Order processOrderAfterPaymentSuccess(Order order);
}
