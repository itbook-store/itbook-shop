package shop.itbook.itbookshop.membergroup.membershiphistory.resultmessageenum;

import lombok.Getter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum MembershipHistoryResultMessageEnum {

    MEMBERSHIP_HISTORY_LIST_SUCCESS("회원등급 이력 리스트 조회에 성공하였습니다.");

    private final String message;

    MembershipHistoryResultMessageEnum(String message) {
        this.message = message;
    }
}
