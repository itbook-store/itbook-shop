package shop.itbook.itbookshop.fileservice.init;

import com.fasterxml.jackson.databind.DeserializationFeature;
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

        LocalDateTime tokenExpiresTime = tokenManager.getToken().getExpires();

        LocalDateTime fiveMinutesLater = LocalDateTime.now().plusMinutes(5);

        return tokenExpiresTime.isAfter(fiveMinutesLater);
    }

    /**
     * 토큰이 존재하는지 판단하는 메서드입니다.
     * 토큰 아이디가 null이면 토큰을 발급 받아 레디스에 저장합니다.
     *
     * @return 토큰 존재 여부에 따른 boolean을 반환합니다.
     * @author 이하늬
     */
    public boolean isTokenExist() {
        String tokenId = tokenManager.getToken().getId();
        if (Objects.isNull(tokenId)) {
            return false;
        }
        return true;
    }

}