package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class CanNotSaveRedisException extends RuntimeException {

    public static final String MESSAGE = "레디스에 저장하는 것을 실패했습니다.";

    public CanNotSaveRedisException() {
        super(MESSAGE);
    }
}
