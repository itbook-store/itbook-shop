package shop.itbook.itbookshop.membergroup.membership.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Builder
public class MembershipResponseDto {

    private Integer membershipNo;
    private String membershipGrade;
    private Long membershipStandardAmount;
    private Long membershipPoint;
}
