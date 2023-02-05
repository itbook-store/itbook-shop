package shop.itbook.itbookshop.membergroup.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 각 회원등급별 회원들의 수를 받아오는 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCountByMembershipResponseDto {

    private Long commonCnt;
    private Long whiteCnt;
    private Long silverCnt;
    private Long goldCnt;
    private Long platinumCnt;
}
