package shop.itbook.itbookshop.membergroup.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Oauth 로그인 시에 멤버 아이디와 인코딩된 비밀번호를 받아오는 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberOauthLoginResponseDto {

    private String memberId;

    private String password;

}
