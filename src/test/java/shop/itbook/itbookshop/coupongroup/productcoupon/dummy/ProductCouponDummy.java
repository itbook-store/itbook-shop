package shop.itbook.itbookshop.coupongroup.productcoupon.dummy;

import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 강명관
 * @since 1.0
 */
public class ProductCouponDummy {

    private ProductCouponDummy() {

    }

    public static ProductCoupon getProductCoupon(Coupon coupon, Product product) {
        return new ProductCoupon(coupon.getCouponNo(), product);
    }
}
