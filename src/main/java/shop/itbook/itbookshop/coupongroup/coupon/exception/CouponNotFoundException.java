package shop.itbook.itbookshop.coupongroup.coupon.exception;

/**
 * 쿠폰이 존재하지 않을때 발생시킬 예외 클래스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class CouponNotFoundException extends RuntimeException {

    public static final String MESSAGE = "쿠폰이 존재하지 않습니다.";

    public CouponNotFoundException() {
        super(MESSAGE);
    }
}
