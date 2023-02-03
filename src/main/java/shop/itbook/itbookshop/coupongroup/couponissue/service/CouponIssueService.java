package shop.itbook.itbookshop.coupongroup.couponissue.service;


import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    Long addCouponIssueByNormalCoupon(String memberId, Long couponNo);
    Integer addCouponIssueByWelcomeCoupon(Member member);
}
