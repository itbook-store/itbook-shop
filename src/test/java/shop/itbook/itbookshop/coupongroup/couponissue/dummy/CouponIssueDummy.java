package shop.itbook.itbookshop.coupongroup.couponissue.dummy;

import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.usagestatus.dummy.UsageStatusDummy;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponIssueDummy {
    public static CouponIssue getCouponIssue() {

        return CouponIssue.builder()
            .member(MemberDummy.getMember1())
            .coupon(CouponDummy.getPercentCoupon())
            .couponExpiredAt(CouponDummy.getPercentCoupon().getCouponExpiredAt())
            .usageStatus(UsageStatusDummy.getAvailableUsageStatus()).build();
    }
}
