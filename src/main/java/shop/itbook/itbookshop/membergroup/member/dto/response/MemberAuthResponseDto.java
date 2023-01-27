package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthResponseDto {
    private Long memberNo;
    private String memberId;
    private String password;
    List<String> roleList;
}
