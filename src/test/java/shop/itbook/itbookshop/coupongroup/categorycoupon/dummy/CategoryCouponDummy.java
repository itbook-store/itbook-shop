package shop.itbook.itbookshop.coupongroup.categorycoupon.dummy;

import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;

/**
 * @author 강명관
 * @since 1.0
 */
public class CategoryCouponDummy {

    private CategoryCouponDummy() {

    }

    public static CategoryCoupon getCategoryCoupon(Coupon coupon, Category category) {
        return new CategoryCoupon(coupon.getCouponNo(), category);
    }
}
