package shop.itbook.itbookshop.coupongroup.couponissue.service;


/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    Long addCouponIssueByNormalCoupon(String memberId, Long couponNo);
}
