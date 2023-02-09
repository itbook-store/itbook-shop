package shop.itbook.itbookshop.paymentgroup.payment.exception;

/**
 * 결제가 유효하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class InvalidOrderException extends RuntimeException {

    public static final String MESSAGE = "유효하지 않은 주문입니다.";

    public InvalidOrderException() {
        super(MESSAGE);
    }
}
