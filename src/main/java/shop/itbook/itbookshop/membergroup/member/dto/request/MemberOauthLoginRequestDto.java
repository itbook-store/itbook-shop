package shop.itbook.itbookshop.membergroup.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소셜 로그인할 때 아이디가 테이블에 존재하는지 찾기위한 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberOauthLoginRequestDto {

    String email;
    String encodedEmail;

}
