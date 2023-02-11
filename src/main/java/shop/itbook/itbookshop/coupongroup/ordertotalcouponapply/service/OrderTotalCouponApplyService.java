package shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface OrderTotalCouponApplyService {
    void saveOrderTotalCouponApplyAndChangeCouponIssue(Long couponIssueNo, Order order);

    void cancelOrderTotalCouponApplyAndChangeCouponIssue(Long couponIssueNo);
}
