package shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.dummy;

import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 강명관
 * @since 1.0
 */
public class OrderTotalCouponApplyDummy {

    private OrderTotalCouponApplyDummy() {

    }

    public static OrderTotalCouponApply getOrderTotalCouponApply(CouponIssue couponIssue,
                                                                 Order order) {
        return new OrderTotalCouponApply(couponIssue.getCouponIssueNo(), order);
    }
}
