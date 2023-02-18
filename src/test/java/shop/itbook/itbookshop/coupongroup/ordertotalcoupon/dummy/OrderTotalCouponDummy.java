package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dummy;

import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;

/**
 * @author 강명관
 * @since 1.0
 */
public class OrderTotalCouponDummy {
    private OrderTotalCouponDummy() {

    }

    public static OrderTotalCoupon getOrderTotalCouponDummy(Coupon coupon) {
        return new OrderTotalCoupon(coupon.getCouponNo());
    }
}
