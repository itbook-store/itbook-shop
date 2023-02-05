package shop.itbook.itbookshop.membergroup.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 상태별 회원들의 수를 받아오는 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCountResponseDto {

    Long memberCount;

    Long blockMemberCount;

    Long withdrawMemberCount;
}
