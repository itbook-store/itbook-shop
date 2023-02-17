package shop.itbook.itbookshop.coupongroup.membershipcoupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class NotMatchCouponException extends RuntimeException {
    private static final String MESSAGE = "등급이 맞지 않습니다.";

    public NotMatchCouponException() {
        super(MESSAGE);
    }
}
