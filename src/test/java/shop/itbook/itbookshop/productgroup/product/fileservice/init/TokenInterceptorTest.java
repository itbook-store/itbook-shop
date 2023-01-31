package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.itbook.itbookshop.fileservice.init.TokenInterceptor;
import shop.itbook.itbookshop.fileservice.init.TokenManager;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductAdminController.class)
public class TokenInterceptorTest {

    @Autowired
    MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @MockBean
        RedisTemplate<String, String> redisTemplate;
        @Autowired
        TokenManager tokenManager;

        @Bean
        public WebMvcConfigurer testWebMvcConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addInterceptors(@NonNull InterceptorRegistry registry) {
                    String apiEndpointPattern = "/**";

                    registry.addInterceptor(new TokenInterceptor(tokenManager, redisTemplate))
                        .addPathPatterns(apiEndpointPattern)
                        .order(0);
                }
            };
        }
    }
}
