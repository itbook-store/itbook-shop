package shop.itbook.itbookshop.membergroup.member.dto.response;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberResponseProjectionDto {

    Long getMemberNo();

    MembershipResponseDto getMembership();

    MemberStatusResponseDto getMemberStatus();

    String getId();

    String getNickname();

    String getName();

    Boolean getIsMan();

    LocalDateTime getBirth();

    String getPassword();

    String getPhoneNumber();

    String getEmail();

    LocalDateTime getMemberCreatedAt();
}
