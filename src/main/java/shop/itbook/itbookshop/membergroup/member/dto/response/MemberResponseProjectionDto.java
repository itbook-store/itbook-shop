package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * 관리자 api 에서 멤버를 조회할 때 정보를 받아올 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public class MemberResponseProjectionDto {

    String id;
    String membershipGrade;
    MemberStatusEnum memberStatusName;
    String nickname;
    String name;
    Boolean isMan;
    LocalDateTime birth;
    String phoneNumber;
    String email;
    LocalDateTime memberCreatedAt;

    @Builder
    public MemberResponseProjectionDto(String id, String membershipGrade,
                                       MemberStatusEnum memberStatusName,
                                       String nickname, String name, Boolean isMan,
                                       LocalDateTime birth, String phoneNumber, String email,
                                       LocalDateTime memberCreatedAt) {
        this.id = id;
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
