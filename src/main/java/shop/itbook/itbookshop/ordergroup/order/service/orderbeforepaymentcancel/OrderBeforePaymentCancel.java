package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePaymentCancel {
    void cancel(Order order);

    void checkOrderStatus(Long orderNo);

    void changeOrderStatusAboutOrderCancel(Order order);
}
