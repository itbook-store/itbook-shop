package shop.itbook.itbookshop.membergroup.memberstatusenum;

import lombok.Getter;

/**
 * 회원 상태에 대한 Enum 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public enum MemberStatusEnum {
    WITHDRAW("탈퇴회원"),
    BLOCK("차단회원"),
    NORMAL("정상회원");

    private final String memberStatus;

    MemberStatusEnum(String memberStatus) {
        this.memberStatus = memberStatus;
    }
}
