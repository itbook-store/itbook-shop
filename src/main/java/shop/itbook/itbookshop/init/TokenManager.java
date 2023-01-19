package shop.itbook.itbookshop.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author 이하늬
 * @since 1.0
 */
@Data
@Component
@RequiredArgsConstructor
public class TokenManager {
    private ItBookObjectStorageToken.Token itBookObjectStorageToken;
    private RedisTemplate redisTemplate;

    public void requestToken() throws JsonProcessingException {
        String tenantId = "fcb81f74e379456b8ca0e091d351a7af";
        String password = "itbook2023";
        String username = "109622@naver.com";
        String authUrl = "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        TokenRequestDto tokenRequest = new TokenRequestDto();
        tokenRequest.getAuth().setTenantId(tenantId);
        tokenRequest.getAuth().getPasswordCredentials().setUsername(username);
        tokenRequest.getAuth().getPasswordCredentials().setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequestDto> httpEntity
            = new HttpEntity<>(tokenRequest, headers);

        ResponseEntity<ItBookObjectStorageToken> response
            = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity,
            ItBookObjectStorageToken.class);

        itBookObjectStorageToken = response.getBody().getAccess().getToken();
    }

}