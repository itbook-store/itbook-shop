package shop.itbook.itbookshop.membergroup.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 테이블에서 검색했을 때 존재하면 true, 없으면 false를 받는 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberBooleanResponseDto {

    private Boolean isExists;
}
