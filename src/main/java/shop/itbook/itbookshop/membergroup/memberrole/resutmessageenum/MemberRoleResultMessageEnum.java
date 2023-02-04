package shop.itbook.itbookshop.membergroup.memberrole.resutmessageenum;

import lombok.Getter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum MemberRoleResultMessageEnum {
    MEMBER_ROLE_DELETE_MESSAGE("멤버의 권한 삭제에 성공하였습니다."),

    MEMBER_ROLE_ADD_MESSAGE("멤버의 권한 추가에 성공하였습니다."),

    MEMBER_ROLE_FIND_MESSAGE("해당 멤버의 권한 찾기에 성공하였습니다.");

    private String successMessage;

    MemberRoleResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
