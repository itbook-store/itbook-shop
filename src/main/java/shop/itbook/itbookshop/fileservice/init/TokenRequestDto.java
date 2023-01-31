package shop.itbook.itbookshop.fileservice.init;

import lombok.Data;

/**
 * 토큰 발급 요청 시 필요한 정보를 담은 토큰 요청 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Data
public class TokenRequestDto {

    private Auth auth = new Auth();

    @Data
    public class Auth {
        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();
    }

    @Data
    public class PasswordCredentials {
        private String username;
        private String password;
    }
}
