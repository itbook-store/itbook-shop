package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service;

import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface CouponIncreasePointHistoryService {

    CouponIncreasePointHistory savePointHistoryAboutCouponIncrease(Member member,
                                                                   CouponIssue couponIssue,
                                                                   Long pointToApply);
}
