package shop.itbook.itbookshop.coupongroup.couponissue.service;

import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    Long addCouponIssueByNormalCoupon(String memberId, Coupon coupon);
}
