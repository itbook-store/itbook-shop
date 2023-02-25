package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface MemberApplicableWhenOrderAfterPaymentSuccess {

    void useCoupon(Order order);

    void usePoint(Order order);
}
