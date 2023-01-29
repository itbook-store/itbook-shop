package shop.itbook.itbookshop.membergroup.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * 관리자 api에서는 멤버 상태만을 변경할 수 있도록 설계한 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberUpdateAdminRequestDto {

    MemberStatusEnum MemberStatusName;
}