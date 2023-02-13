package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class MismatchProductNoWhenCouponApplyException extends RuntimeException {

    public static String MESSAGE = "요청된 상품쿠폰의 적용 상품과 실제 주문할 상품이 일치하지 않습니다.";

    public MismatchProductNoWhenCouponApplyException() {
        super(MESSAGE);
    }
}
