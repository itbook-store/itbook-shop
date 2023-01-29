package shop.itbook.itbookshop.membergroup.member.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 소셜 로그인할 때 아이디가 테이블에 존재하는지 찾기위한 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
public class MemberOauthLoginRequestDto {

    String memberId;

}
