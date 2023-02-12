package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 멤버 정보를 반환할 Dto 클래스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberNo;
    private String memberId;
    private String membershipGrade;
    private String memberStatusName;
    private String nickname;
    private String name;
    private Boolean isMan;
    private LocalDateTime birth;
    private String password;
    private String phoneNumber;
    private String email;
    private LocalDateTime memberCreatedAt;
    private Boolean isSocial;
    private Boolean isWriter;
}
