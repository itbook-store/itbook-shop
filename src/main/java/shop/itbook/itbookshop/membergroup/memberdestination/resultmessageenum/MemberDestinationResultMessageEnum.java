package shop.itbook.itbookshop.membergroup.memberdestination.resultmessageenum;

import lombok.Getter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum MemberDestinationResultMessageEnum {
    MEMBER_DESTINATION_FIND_MESSAGE("멤버 배송지 정보를 찾는데 성공하였습니다."),
    MEMBER_DESTINATION_DELETE_MESSAGE("멤버 배송지를 삭제하는데 성공하였습니다."),
    MEMBER_DESTINATION_SAVE_MESSAGE("멤버 배송지를 등록하는데 성공하였습니다"),
    MEMBER_DESTINATION_MODIFY_MESSAGE("멤버 배송지를 수정하는데 성공하였습니다.");

    private String successMessage;

    MemberDestinationResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
