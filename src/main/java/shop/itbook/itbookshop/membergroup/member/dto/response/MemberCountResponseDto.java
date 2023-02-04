package shop.itbook.itbookshop.membergroup.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
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
