package shop.itbook.itbookshop.ordergroup.order.service.memberapply;

import shop.itbook.itbookshop.ordergroup.order.dto.CouponApplyDtosAboutProductAndTotal;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface MemberApplicableWhenOrder {

    void useCoupon(Order order);

    void usePoint(Order order);
}
