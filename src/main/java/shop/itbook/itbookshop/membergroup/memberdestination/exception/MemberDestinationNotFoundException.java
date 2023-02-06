package shop.itbook.itbookshop.membergroup.memberdestination.exception;

/**
 * 테이블에 회원 배송지가 존재하지 않을 때 발생시키는 예외입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberDestinationNotFoundException extends RuntimeException {

    public static final String MESSAGE = "해당 회원 배송지가 존재하지 않습니다.";

    public MemberDestinationNotFoundException() {
        super(MESSAGE);
    }
}
