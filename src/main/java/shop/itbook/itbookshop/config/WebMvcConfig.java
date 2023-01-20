package shop.itbook.itbookshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.TokenInterceptor;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.TokenManager;

/**
 * @author 이하늬
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RedisTemplate<String, String> redisTemplate;
    private final TokenManager tokenManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(tokenManager, redisTemplate))
            .addPathPatterns("/**");
    }
}

