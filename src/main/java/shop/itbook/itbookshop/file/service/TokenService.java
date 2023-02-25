package shop.itbook.itbookshop.file.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.file.exception.InvalidTokenException;
import shop.itbook.itbookshop.file.dto.ItBookObjectStorageToken;
import shop.itbook.itbookshop.file.exception.TokenFailureMessage;
import shop.itbook.itbookshop.file.dto.TokenRequestDto;

/**
 * 애플리케이션 구동 시 동작시킬 토큰매니저 클래스입니다.
 * 오브젝트 스토리지 인증 토큰 발급을 요청하여 받아옵니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Data
@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String AUTH_URL =
        "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    public static final String TOKEN_NAME = "ITBOOK-OBJECTSTORAGE_TOKEN";
    @Value("${object.storage.tenant-id}")
    private String tenantId;
    @Value("${object-storage.password}")
    private String password;
    @Value("${object-storage.username}")
    private String username;

    /**
     * rest template으로 토큰 발급 요청을 하여 발급 받은 토큰의 id, 만료 시간을 레디스에 저장합니다.
     *
     * @return 발급 받은 오브젝트 스토리지 토큰을 반환합니다.
     * @author 이하늬
     */
    public ItBookObjectStorageToken.Access.Token requestToken() {

        TokenRequestDto tokenRequest = new TokenRequestDto();
        tokenRequest.getAuth().setTenantId(tenantId);
        tokenRequest.getAuth().getPasswordCredentials().setUsername(username);
        tokenRequest.getAuth().getPasswordCredentials().setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<TokenRequestDto> httpEntity = new HttpEntity<>(tokenRequest, headers);

        ResponseEntity<ItBookObjectStorageToken> response
            = restTemplate.exchange(AUTH_URL, HttpMethod.POST, httpEntity,
            ItBookObjectStorageToken.class);

        ItBookObjectStorageToken.Access.Token itBookObjectStorageToken =
            Objects.requireNonNull(response.getBody()).getAccess().getToken();

        if (Objects.isNull(itBookObjectStorageToken)) {
            throw new InvalidTokenException(
                TokenFailureMessage.INVALID_TOKEN_MESSAGE.getMessage());
        }

        saveTokenInRedis(itBookObjectStorageToken);

        return itBookObjectStorageToken;
    }

    private void saveTokenInRedis(ItBookObjectStorageToken.Access.Token itBookObjectStorageToken) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            redisTemplate.opsForValue()
                .set(TOKEN_NAME, mapper.registerModule(new JavaTimeModule())
                    .writeValueAsString(itBookObjectStorageToken));
            long duration =
                Duration.between(LocalDateTime.now(), itBookObjectStorageToken.getExpires())
                    .getSeconds();
            redisTemplate.expire(TOKEN_NAME, duration, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new InvalidTokenException(
                TokenFailureMessage.FAILURE_REQUEST_TOKEN_MESSAGE.getMessage());
        }
    }

    /**
     * 레디스에서 토큰을 가져오는 메서드입니다.
     *
     * @return 레디스에서 얻은 값을 반환합니다.
     * @author 이하늬
     */
    public ItBookObjectStorageToken.Access.Token getToken() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        try {
            ItBookObjectStorageToken.Access.Token token =
                mapper.readValue(redisTemplate.opsForValue().get(TOKEN_NAME),
                    ItBookObjectStorageToken.Access.Token.class);

            if (Objects.isNull(token)) {
                throw new InvalidTokenException(
                    TokenFailureMessage.INVALID_TOKEN_MESSAGE.getMessage());
            }

            return token;
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidTokenException(
                TokenFailureMessage.INVALID_TOKEN_MESSAGE.getMessage());
        }
    }

}