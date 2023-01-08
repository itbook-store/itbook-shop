package shop.itbook.itbookshop.common.exception;

/**
 * 사용자가 접근권한이 없을때 예외를 발생시키기 위한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class MemberForbiddenException extends RuntimeException {

    public static final String MESSAGE = "사용자의 접근 권한이 없습니다.";

    public MemberForbiddenException() {
        super(MESSAGE);
    }
}
