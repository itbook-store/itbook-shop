package shop.itbook.itbookshop.membergroup.membership.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 요청 수행 시 반환 메세지를 열거한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public enum MembershipResultMessageEnum {

    MEMBERSHIP_CREATE_SUCCESS("회원등급 생성에 성공하였습니다."),
    MEMBERSHIP_DELETE_SUCCESS("회원등급 삭제에 성공하였습니다."),
    MEMBERSHIP_MODIFY_SUCCESS("회원등급 수정에 성공하였습니다."),

    MEMBERSHIP_LIST_SUCCESS("회원등급 리스트 찾기에 성공하였습니다."),

    MEMBERSHIP_FIND_SUCCESS("회원등급 찾기에 성공하였습니다.");

    private final String message;

    MembershipResultMessageEnum(String message) {
        this.message = message;
    }
}
