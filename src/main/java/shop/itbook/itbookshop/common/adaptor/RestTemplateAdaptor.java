package shop.itbook.itbookshop.common.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.common.response.CommonResponseBody;

/**
 * RestTemplate 통신 메서드를 제공해주는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@RequiredArgsConstructor
@Component
public class RestTemplateAdaptor {

    private final RestTemplate restTemplate;

    public <T> ResponseEntity<CommonResponseBody<T>> getAdaptor(
        String url) {

        return restTemplate.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<>(new HttpHeaders()),
            new ParameterizedTypeReference<CommonResponseBody<T>>() {
            });
    }

    public <T> ResponseEntity<CommonResponseBody<T>> postAdaptor(String url,
                                                                 HttpEntity http) {
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            http,
            new ParameterizedTypeReference<>() {
            }
        );
    }
}