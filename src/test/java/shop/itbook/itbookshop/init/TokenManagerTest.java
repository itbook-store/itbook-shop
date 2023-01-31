package shop.itbook.itbookshop.init;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.config.RedisConfig;
import shop.itbook.itbookshop.fileservice.init.TokenManager;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
@EnableConfigurationProperties(value = RedisConfig.class)
@ContextConfiguration(classes = TokenManager.class)
@Slf4j
class TokenManagerTest {

    @Autowired
    TokenManager tokenManager;

    @Test
    void getToken() {
        String id = tokenManager.requestToken().getId();
        log.info(id);
    }
}