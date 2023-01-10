package shop.itbook.itbookshop.membergroup.member.exception;

/**
 * 특정 memberNo의 멤버가 존재하지 않을 때 발생시키는 예외입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당 memberNo가 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

}
