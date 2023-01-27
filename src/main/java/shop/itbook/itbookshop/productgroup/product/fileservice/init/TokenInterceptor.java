package shop.itbook.itbookshop.productgroup.product.fileservice.init;

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
    private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

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
     * 토큰 만료 시간이 현재 시간의 5분 전보다 후이면 유효 토큰이라고 판단
     *
     * @return true: 유효 토큰 / false: 유효하지 않은 토큰
     * @author 이하늬
     */
    public boolean isTokenValid() {

        if (!isTokenExist()) {
            return false;
        }

        String tokenExpires = getTokenFields("tokenExpires");
        LocalDateTime tokenExpiresTime = LocalDateTime.parse(tokenExpires, sdf);
        LocalDateTime fiveMinutesBefore = LocalDateTime.now().minusMinutes(5);

        return tokenExpiresTime.isAfter(fiveMinutesBefore);
    }

    /**
     * 레디스에서 특정 필드 값을 가져오는 메서드입니다.
     *
     * @param fieldName 필드 이름
     * @return 레디스에서 얻은 값을 반환합니다.
     * @author 이하늬
     */
    public String getTokenFields(String fieldName) {
        return (String) redisTemplate.opsForHash()
            .get("ITBOOK-OBJECTSTORAGE_TOKEN", fieldName);
    }

    /**
     * 토큰이 존재하는지 판단하는 메서드입니다.
     * 토큰 아이디가 null이면 예외를 발생시킵니다.
     *
     * @return 토큰 존재 여부에 따른 boolean을 반환합니다.
     * @author 이하늬
     */
    public boolean isTokenExist() {
        String tokenId = getTokenFields("tokenId");
        if (Objects.isNull(tokenId)) {
            throw new InvalidTokenException();
        }
        return true;
    }

}