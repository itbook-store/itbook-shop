package shop.itbook.itbookshop.coupongroup.categorycouponapply.dummy;

import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 강명관
 * @since 1.0
 */
public class CategoryCouponApplyDummy {

    private CategoryCouponApplyDummy() {

    }

    public static CategoryCouponApply getCategoryCouponApply(CouponIssue couponIssue,
                                                             OrderProduct orderProduct) {
        return new CategoryCouponApply(couponIssue.getCouponIssueNo(), orderProduct);
    }
}
