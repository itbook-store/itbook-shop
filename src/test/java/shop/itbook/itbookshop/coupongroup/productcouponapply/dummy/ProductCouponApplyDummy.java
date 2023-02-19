package shop.itbook.itbookshop.coupongroup.productcouponapply.dummy;

import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 강명관
 * @since 1.0
 */
public class ProductCouponApplyDummy {

    private ProductCouponApplyDummy() {

    }

    public static ProductCouponApply getProductCouponApply(CouponIssue couponIssue,
                                                           OrderProduct orderProduct) {
        return new ProductCouponApply(couponIssue.getCouponIssueNo(), orderProduct);
    }
}
