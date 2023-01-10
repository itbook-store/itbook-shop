package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;

/**
 * 멤버 정보를 반환할 Dto 클래스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Builder
public class MemberResponseDto {

    private Long memberNo;
    private MembershipResponseDto membership;
    private MemberStatusResponseDto memberStatus;
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
