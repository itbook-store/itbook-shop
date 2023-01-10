package shop.itbook.itbookshop.membergroup.memberstatus.dto.response;

import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * 멤버 상태 정보를 반환할 Dto 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusResponseDto {

    Integer getMemberStatusNo();

    MemberStatusEnum getMemberStatusEnum();
}
