package shop.itbook.itbookshop.ordergroup.order.util;

import java.util.Objects;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;

/**
 * @author 최겸준
 * @since 1.0
 */
public class AmountCalculationBeforePaymentUtil {

    private AmountCalculationBeforePaymentUtil() {
    }

    public static Coupon getAvailableCoupon(CouponIssue couponIssue,
                                            long basePriceToCompareAboutStandardAmount) {

        Coupon coupon = couponIssue.getCoupon();

        Long standardAmount = coupon.getStandardAmount();
        if (basePriceToCompareAboutStandardAmount < standardAmount) {
            throw new CanNotApplyCouponException();
        }

        return coupon;
    }

    public static long subAmountToDiscountedPriceAndNegativeCheck(long amount,
                                                                  long discountedPrice) {
        amount -= discountedPrice;
        if (amount < 0) {
            amount = 0;
        }

        return amount;
    }

    public static long getTotalPriceWithCouponApplied(Coupon coupon, long priceToApplyCoupon,
                                                      long basePriceToCalcDiscountPercent) {
        Long couponAmount = coupon.getAmount();
        Integer couponPercent = coupon.getPercent();

        if (Objects.equals(couponPercent, 0)) {
            priceToApplyCoupon -= couponAmount;
        } else {
            long discountPrice = (long) (basePriceToCalcDiscountPercent * couponPercent * 0.01);
            priceToApplyCoupon -= discountPrice;
        }

        return priceToApplyCoupon;
    }
}
