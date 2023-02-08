package shop.itbook.itbookshop.productgroup.product.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class InvalidInputException extends RuntimeException {

    public static final String MESSAGE = "유효하지 않은 입력값입니다. 입력 값을 다시 확인해주세요!";

    public InvalidInputException() {
        super(MESSAGE);
    }
}
