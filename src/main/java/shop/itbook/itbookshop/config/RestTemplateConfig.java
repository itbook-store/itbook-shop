package shop.itbook.itbookshop.config;

import java.time.Duration;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 를 스프링 빈으로 등록하고 관리하기 위한 설정 클래스 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Rest Template 을 설정하고 빈으로 등록합니다.
     *
     * @return 설정 완료된 Rest template 을 반환합니다.
     * @author 정재원 *
     */
    @Bean
    @Qualifier("DefaultRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setReadTimeout(Duration.ofSeconds(3L))
            .setConnectTimeout(Duration.ofSeconds(3L))
            .build();
    }
}
