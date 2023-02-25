package shop.itbook.itbookshop.ordergroup.order.service.memberapply.orderbeforepayment;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface MemberApplicableWhenOrderBeforePayment {

    void useCoupon(Order order);

    void usePoint(Order order);
}
