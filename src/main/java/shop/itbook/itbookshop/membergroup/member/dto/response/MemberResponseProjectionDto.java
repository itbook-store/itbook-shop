package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * 관리자 api 에서 멤버를 조회할 때 정보를 받아올 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public class MemberResponseProjectionDto {

    Long memberNo;
    String memberId;
    String membershipGrade;
    String memberStatusName;
    String nickname;
    String name;
    Boolean isMan;
    LocalDateTime birth;
    String phoneNumber;
    String email;
    LocalDateTime memberCreatedAt;

    @SuppressWarnings("java:S107") // 회원 테이블의 입력 받아야 될 필드값이 많기 때문
    @Builder
    public MemberResponseProjectionDto(Long memberNo, String memberId, String membershipGrade,
                                       String memberStatusName, String nickname, String name,
                                       Boolean isMan, LocalDateTime birth, String phoneNumber,
                                       String email, LocalDateTime memberCreatedAt) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.membershipGrade = membershipGrade;
        this.memberStatusName = memberStatusName;
        this.nickname = nickname;
        this.name = name;
        this.isMan = isMan;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memberCreatedAt = memberCreatedAt;
    }
}
