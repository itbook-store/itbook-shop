package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import java.util.Objects;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 어플리케이션 구동 시 동작시킬 토큰매니저 클래스입니다.
 * 오브젝트 스토리지 인증 토큰 발급을 요청하여 받아옵니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Data
@Component
public class TokenManager implements InitializingBean {
    private final String AUTH_URL =
        "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";
    private final RestTemplate restTemplate = new RestTemplate();
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TOKEN_NAME = "ITBOOK-OBJECTSTORAGE_TOKEN";


    @Override
    public void afterPropertiesSet() {
        requestToken();
    }

    /**
     * rest template으로 토큰 발급 요청을 하여 발급 받은 토큰의 id, 만료시간을 레디스에 저장합니다.
     *
     * @return 발급 받은 오브젝트 스토리지 토큰을 반환합니다.
     * @author 이하늬
     */
    public ItBookObjectStorageToken.Token requestToken() {
        String tenantId = "fcb81f74e379456b8ca0e091d351a7af";
        String password = "itbook2023";
        String username = "109622@naver.com";

        TokenRequestDto tokenRequest = new TokenRequestDto();
        tokenRequest.getAuth().setTenantId(tenantId);
        tokenRequest.getAuth().getPasswordCredentials().setUsername(username);
        tokenRequest.getAuth().getPasswordCredentials().setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<TokenRequestDto> httpEntity
            = new HttpEntity<>(tokenRequest, headers);

        ResponseEntity<ItBookObjectStorageToken> response
            = restTemplate.exchange(AUTH_URL, HttpMethod.POST, httpEntity,
            ItBookObjectStorageToken.class);

        ItBookObjectStorageToken.Token itBookObjectStorageToken =
            response.getBody().getAccess().getToken();

        if (Objects.isNull(itBookObjectStorageToken)) {
            throw new InvalidTokenException();
        }

        redisTemplate.opsForHash()
            .put(TOKEN_NAME, "tokenId", itBookObjectStorageToken.getId());
        redisTemplate.opsForHash()
            .put(TOKEN_NAME, "tokenExpires",
                itBookObjectStorageToken.getExpires());

        return itBookObjectStorageToken;
    }


}