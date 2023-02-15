package shop.itbook.itbookshop.membergroup.memberdestination.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class MemberDestinationComeCloseOtherMemberException extends RuntimeException {

    public static final String MESSAGE = "다른 회원의 배송지에는 접근할 수 없습니다.";

    public MemberDestinationComeCloseOtherMemberException() {
        super(MESSAGE);
    }
}
