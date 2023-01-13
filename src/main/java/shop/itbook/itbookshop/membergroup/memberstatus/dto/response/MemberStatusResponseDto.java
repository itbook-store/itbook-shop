package shop.itbook.itbookshop.membergroup.memberstatus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 상태 정보를 반환할 Dto 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatusResponseDto {

    private Integer memberStatusNo;

    private String memberStatusName;
}
