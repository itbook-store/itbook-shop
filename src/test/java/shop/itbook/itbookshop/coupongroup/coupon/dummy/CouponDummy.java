package shop.itbook.itbookshop.coupongroup.coupon.dummy;

import java.time.LocalDateTime;
import java.util.UUID;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponDummy {
    public static Coupon getPointCoupon() {

        return Coupon.builder()
            .name("포인트 쿠폰")
            .point(10000L)
            .amount(0L)
            .percent(0)
            .couponCreatedAt(LocalDateTime.now())
            .couponExpiredAt(LocalDateTime.of(2023,01,31,0,0))
            .code(UUID.randomUUID().toString())
            .isDuplicateUse(false)
            .totalQuantity(1)
            .build();
    }

    public static Coupon getAmountCoupon() {
        return Coupon.builder()
            .name("금액 쿠폰")
            .amount(10000L)
            .point(0L)
            .percent(0)
            .standardAmount(1000L)
            .couponCreatedAt(LocalDateTime.now())
            .couponExpiredAt(LocalDateTime.of(2023,01,31,0,0))
            .code(UUID.randomUUID().toString())
            .isDuplicateUse(false)
            .totalQuantity(1)
            .build();
    }

    public static Coupon getPercentCoupon() {
        return Coupon.builder()
            .name("쿠폰")
            .percent(10)
            .amount(0L)
            .point(0L)
            .standardAmount(1000L)
            .maxDiscountAmount(10000L)
            .couponCreatedAt(LocalDateTime.now())
            .couponExpiredAt(LocalDateTime.of(2023,01,31,0,0))
            .code(UUID.randomUUID().toString())
            .isDuplicateUse(false)
            .totalQuantity(0)
            .build();
    }
}
