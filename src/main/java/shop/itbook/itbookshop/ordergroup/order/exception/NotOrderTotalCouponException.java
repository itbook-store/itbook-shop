package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class NotOrderTotalCouponException extends RuntimeException {

    public static final String MESSAGE = "주문총액쿠폰으로 적용하려고 요청하신 해당 쿠폰은 주문총액 쿠폰이 아닙니다.";

    public NotOrderTotalCouponException() {
        super(MESSAGE);
    }
}
