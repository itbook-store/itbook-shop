package shop.itbook.itbookshop.membergroup.membership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 멤버십 response dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MembershipResponseDto {

    private Integer membershipNo;
    private String membershipGrade;
    private Long membershipStandardAmount;
    private Long membershipPoint;
}
