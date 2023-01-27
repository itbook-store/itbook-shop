package shop.itbook.itbookshop.membergroup.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 인증을 위해 필요한 데이터를 전달하기 위한 DTO입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthInfoResponseDto {

    private Long memberNo;

    private String memberId;

    private String password;


}
