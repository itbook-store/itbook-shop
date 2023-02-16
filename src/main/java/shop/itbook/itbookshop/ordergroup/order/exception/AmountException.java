package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * 결제 금액이 100원 이하일 시 발생하는 예외입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class AmountException extends RuntimeException {

    public static final String MESSAGE = "100원 이하는 결제할 수 없습니다. 요청한 금액: ";

    public AmountException(Long amount) {
        super(MESSAGE + amount);
    }
}
