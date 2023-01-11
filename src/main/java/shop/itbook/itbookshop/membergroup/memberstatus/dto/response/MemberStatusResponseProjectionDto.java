package shop.itbook.itbookshop.membergroup.memberstatus.dto.response;

import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * 멤버 상태 응답 인터페이스 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusResponseProjectionDto {

    Integer getMemberStatusNo();

    MemberStatusEnum getMemberStatusEnum();
}
