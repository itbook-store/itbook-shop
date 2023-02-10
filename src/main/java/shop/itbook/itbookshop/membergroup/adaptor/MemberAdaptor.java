package shop.itbook.itbookshop.membergroup.adaptor;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.config.ServerConfig;
import shop.itbook.itbookshop.membergroup.member.dto.response.EncodeResponseDto;

/**
 * Auth 서버에 암호화 정책에 따른 비밀번호 암호화를 요청하는 Adaptor 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdaptor {

    private final ServerConfig serverConfig;

    private final RestTemplate restTemplate;

    private static final String AUTH_ENCODE_API = "/auth/encode";

    public EncodeResponseDto postEncodeForPassword(EncodeResponseDto encodeResponseDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<EncodeResponseDto>> exchange = restTemplate.exchange(
            serverConfig.getAuthUrl() + AUTH_ENCODE_API,
            HttpMethod.POST,
            new HttpEntity<>(encodeResponseDto, headers),
            new ParameterizedTypeReference<>() {
            }
        );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }


}
