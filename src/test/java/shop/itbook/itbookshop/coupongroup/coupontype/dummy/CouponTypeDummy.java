package shop.itbook.itbookshop.coupongroup.coupontype.dummy;

import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponTypeDummy {
    public static CouponType getCouponType(){
        return new CouponType(1, CouponTypeEnum.NORMAL_COUPON);
    }
    public static CouponType getWelcomeCouponType(){
        return new CouponType(2, CouponTypeEnum.WELCOME_COUPON);
    }
    public static CouponType getMonthlyCouponType(){
        return new CouponType(3, CouponTypeEnum.MONTHLY_COUPON);
    }
}