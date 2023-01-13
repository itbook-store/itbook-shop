package shop.itbook.itbookshop.config;

import java.util.Collections;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
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

    @Bean
    @Qualifier("DefaultRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

        HttpComponentsClientHttpRequestFactory factory =
            new HttpComponentsClientHttpRequestFactory();

        HttpClient client = HttpClientBuilder.create()
            .setMaxConnTotal(20)
            .setMaxConnPerRoute(10)
            .build();

        factory.setHttpClient(client);
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(5000);

        return new RestTemplate(factory);
    }
}
