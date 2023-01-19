package shop.itbook.itbookshop.init;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TokenManager.class})
class TokenManagerTest {

    @Autowired
    TokenManager tokenManager;

    final String TOKEN_ID =
        "gAAAAABjyM0rbUs68CZ3w0kyFR2uuJzvbkDCaqqKccgr4Qpy3sh9Bxj2D1rJVvGCMqupPSrHQZfoa2yAhIN4oyGK1uk3yvcRT2QrHjJiuTCM_F4hzbbCM-G3meYCBoo1p3_lM4Yiju2_l0LGjT0X6D0zioPmWaWgg5nWDhzlAPXdwpAMvlUOv2w";
    final String TOKEN_EXPIRES = "2023-01-19T16:55:07Z";

    @Test
    void getToken() throws JsonProcessingException {
        tokenManager.requestToken();

        assertNotNull(tokenManager.getItBookObjectStorageToken());
    }
}