package shop.itbook.itbookshop.auth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인된 회원의 정보를 게이트웨이에서부터 받아와서 사용하기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationHeaderDto {
    private Long memberNo;
    private List<String> role;
}
