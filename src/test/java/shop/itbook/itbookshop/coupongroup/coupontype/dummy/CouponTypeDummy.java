package shop.itbook.itbookshop.coupongroup.coupontype.dummy;

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
}