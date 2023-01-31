package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import static shop.itbook.itbookshop.productgroup.product.fileservice.init.TokenManager.TOKEN_NAME;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 토큰이 유효한지 유효하지 않은지 판단하여 토큰을 갱신하는 토큰 인터셉터입니다.
 *
 * @author 이하늬 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;
    private final RedisTemplate<String, String> redisTemplate;
    private DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        if (!isTokenValid()) {
            tokenManager.requestToken();
        }
        return true;
    }

    /**
     * 토큰 유효 여부를 확인.
     * 현재 시간의 5분 후보다 토큰 만료 시간이 더 늦으면 유효 토큰이라고 판단
     *
     * @return true: 유효 토큰 / false: 유효하지 않은 토큰
     * @author 이하늬
     */
    public boolean isTokenValid() {

        if (!isTokenExist()) {
            return false;
        }

//        String tokenExpires = getTokenFields("tokenExpires");
//        LocalDateTime tokenExpiresTime = LocalDateTime.parse(tokenExpires, dateTimeFormatter);

        LocalDateTime tokenExpiresTime = getToken().getExpires();

        LocalDateTime fiveMinutesLater = LocalDateTime.now().plusMinutes(5);

        return tokenExpiresTime.isAfter(fiveMinutesLater);
    }

    /**
     * 레디스에서 토큰을 가져오는 메서드입니다.
     *
     * @return 레디스에서 얻은 값을 반환합니다.
     * @author 이하늬
     */
    public Token getToken() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.readValue((String) redisTemplate.opsForHash()
                .get(TOKEN_NAME, "token"), Token.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 토큰이 존재하는지 판단하는 메서드입니다.
     * 토큰 아이디가 null이면 토큰을 발급 받아 레디스에 저장합니다.
     *
     * @return 토큰 존재 여부에 따른 boolean을 반환합니다.
     * @author 이하늬
     */
    public boolean isTokenExist() {
        String tokenId = getToken().getId();
        if (Objects.isNull(tokenId)) {
            return false;
        }
        return true;
    }

}