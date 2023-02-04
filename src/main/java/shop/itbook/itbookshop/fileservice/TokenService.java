package shop.itbook.itbookshop.fileservice;

import java.util.Objects;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.fileservice.exception.InvalidTokenException;
import shop.itbook.itbookshop.fileservice.dto.ItBookObjectStorageToken;
import shop.itbook.itbookshop.fileservice.dto.Token;
import shop.itbook.itbookshop.fileservice.exception.TokenFailureMessage;
import shop.itbook.itbookshop.fileservice.dto.TokenRequestDto;

/**
 * 애플리케이션 구동 시 동작시킬 토큰매니저 클래스입니다.
 * 오브젝트 스토리지 인증 토큰 발급을 요청하여 받아옵니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Data
@Service
public class TokenService {
    private final String AUTH_URL =
        "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";
    private final RestTemplate restTemplate = new RestTemplate();
    private final RedisTemplate<String, String> redisTemplate;
    public static final String TOKEN_NAME = "ITBOOK-OBJECTSTORAGE_TOKEN";
    @Value("${object.storage.tenant-id}")
    private String tenantId = "fcb81f74e379456b8ca0e091d351a7af";
    @Value("${object-storage.password}")
    private String password = "itbook2023";
    @Value("${object-storage.username}")
    private String username = "109622@naver.com";

    /**
     * rest template으로 토큰 발급 요청을 하여 발급 받은 토큰의 id, 만료 시간을 레디스에 저장합니다.
     *
     * @return 발급 받은 오브젝트 스토리지 토큰을 반환합니다.
     * @author 이하늬
     */
    public Token requestToken() {

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

        Token itBookObjectStorageToken = response.getBody().getAccess().getToken();

        if (Objects.isNull(itBookObjectStorageToken)) {
            throw new InvalidTokenException(
                TokenFailureMessage.FAILURE_INVALID_MESSAGE.getMessage());
        }

        return itBookObjectStorageToken;
    }

}