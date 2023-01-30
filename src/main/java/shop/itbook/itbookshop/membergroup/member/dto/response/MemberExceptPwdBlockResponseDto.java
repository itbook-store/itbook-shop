package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 api 에서 차단 멤버를 조회할 때 정보를 받아올 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberExceptPwdBlockResponseDto {

    private Long memberNo;
    private String memberId;
    private String membershipGrade;
    private String memberStatusName;
    private String nickname;
    private String name;
    private Boolean isMan;
    private LocalDateTime birth;
    private String phoneNumber;
    private String email;
    private LocalDateTime memberCreatedAt;
    private String statusChangedReason;
    private LocalDateTime memberStatusHistoryCreatedAt;
}
