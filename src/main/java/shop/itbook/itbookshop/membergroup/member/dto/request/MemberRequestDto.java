package shop.itbook.itbookshop.membergroup.member.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * @author 노수연
 * @since 1.0
 */

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private Membership membership;
    private MemberStatus memberStatus;
    private String id;
    private String nickname;
    private String name;
    private Boolean isMan;
    private LocalDateTime birth;
    private String password;
    private String phoneNumber;
    private String email;
    private LocalDateTime memberCreatedAt;
}
