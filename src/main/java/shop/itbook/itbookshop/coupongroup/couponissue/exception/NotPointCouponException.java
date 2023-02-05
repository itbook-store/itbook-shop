package shop.itbook.itbookshop.coupongroup.couponissue.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class NotPointCouponException extends RuntimeException {
    private static final String MESSAGE = "포인트 쿠폰이 아닙니다.";

    public NotPointCouponException() {
        super(MESSAGE);
    }
}
