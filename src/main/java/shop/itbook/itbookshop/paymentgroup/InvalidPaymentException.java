package shop.itbook.itbookshop.paymentgroup;

/**
 * 결제가 유효하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class InvalidPaymentException extends RuntimeException {

    public static final String MESSAGE = "유효하지 않은 결제입니다.";

    public InvalidPaymentException() {
        super(MESSAGE);
    }
}
