package shop.itbook.itbookshop.init;

import lombok.Data;

/**
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
