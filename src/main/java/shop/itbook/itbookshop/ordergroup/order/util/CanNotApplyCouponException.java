package shop.itbook.itbookshop.ordergroup.order.util;

/**
 * @author 최겸준
 * @since 1.0
 */
public class CanNotApplyCouponException extends RuntimeException {
    public static final String MESSAGE = "쿠폰을 적용할수 없습니다.";

    public CanNotApplyCouponException() {
        super(MESSAGE);
    }

    public CanNotApplyCouponException(String message) {
        super(message);
    }
}
