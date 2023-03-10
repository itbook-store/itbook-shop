package shop.itbook.itbookshop.membergroup.member.resultmessageenum;

import lombok.Getter;

/**
 * 멤버 컨트롤러에서 요청을 보내고 성공했을 때 반환 할 성공 메세지 enum 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum MemberResultMessageEnum {

    MEMBER_SAVE_SUCCESS_MESSAGE("멤버 삽입에 성공하였습니다."),
    MEMBER_FIND_SUCCESS_MESSAGE("특정 멤버를 불러오는데 성공하였습니다."),
    MEMBER_LIST_SUCCESS_MESSAGE("모든 멤버 반환에 성공하였습니다."),
    MEMBER_MODIFY_SUCCESS_MESSAGE("멤버 수정에 성공하였습니다."),
    MEMBER_EMAIL_EXISTS_TRUE_MESSAGE("이미 존재하는 이메일입니다."),
    MEMBER_SOCIAL_LOGIN_MESSAGE("소셜 로그인이나 소셜 회원가입이 가능합니다."),
    MEMBER_COUNT_SUCCESS_MESSAGE("멤버 총합을 가져오는데 성공하였습니다."),
    MEMBER_POINT_FIND_SUCCESS_MESSAGE("멤버 포인트를 가져오는데 성공하였습니다."),

    MEMBER_POINT_GIFT_SUCCESS_MESSAGE("멤버끼리 포인트를 선물하는데 성공하였습니다.");

    private String successMessage;

    MemberResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
