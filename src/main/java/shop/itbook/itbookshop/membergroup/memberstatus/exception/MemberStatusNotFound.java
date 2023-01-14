package shop.itbook.itbookshop.membergroup.memberstatus.exception;


/**
 * @author 강명관
 * @since 1.0
 */
public class MemberStatusNotFound extends RuntimeException {

    public static final String MESSAGE = "해당 회원 상태가 존재하지 않습니다";

    public MemberStatusNotFound() {
        super(MESSAGE);
    }
}
