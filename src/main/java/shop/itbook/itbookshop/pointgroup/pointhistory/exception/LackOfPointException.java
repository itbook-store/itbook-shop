package shop.itbook.itbookshop.pointgroup.pointhistory.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class LackOfPointException extends RuntimeException {
    public static final String MESSAGE = "포인트가 부족합니다.";

    public LackOfPointException() {
        super(MESSAGE);
    }
}
