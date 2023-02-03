package shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum;

import lombok.Getter;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum CouponTypeEnum {
    NORMAL_COUPON("일반쿠폰"),
    WELCOME_COUPON("웰컴쿠폰"),
    BIRTHDAY_COUPON("생일쿠폰"),
    MONTHLY_COUPON("이달의쿠폰예약형"),
    MEMBERSHIP_COUPON("이달의쿠폰등급형");

    private final String couponType;

    CouponTypeEnum(String couponType) {
        this.couponType = couponType;
    }

    public static CouponTypeEnum stringToEnum(String s) {
        for (CouponTypeEnum value : CouponTypeEnum.values()) {
            if (value.getCouponType().equals(s)) {
                return value;
            }
        }
        return null;
    }
}
