package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * 멤버 정보를 반환할 Dto 클래스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberResponseDto {

    Long getMemberNo();

    Membership getMembership();

    MemberStatus getMemberStatus();

    String getId();

    String getNickname();

    String getName();

    Boolean isMan();

    LocalDateTime getBirth();

    String getPassword();

    String getPhoneNumber();

    String getEmail();

    LocalDateTime getMemberCreatedAt();

}
