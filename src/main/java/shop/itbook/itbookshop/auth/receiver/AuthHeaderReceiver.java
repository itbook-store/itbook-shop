package shop.itbook.itbookshop.auth.receiver;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.itbook.itbookshop.auth.dto.AuthorizationHeaderDto;
import shop.itbook.itbookshop.auth.exception.InvalidAuthRequestException;

/**
 * 인가받은 회원정보를 Request Header 통해 들어온 것을 DTO로 가공해서 받아주는 클래스입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class AuthHeaderReceiver {

    private static final String HEADER_MEMBER_NO = "Authorization-MemberNo";
    private static final String HEADER_MEMBER_ROLE = "Authorization-MemberRole";

    private AuthHeaderReceiver() {
    }

    /**
     * 헤더로 받아온 정보를 DTO 변환 메서드 입니다.
     *
     * @return the auth header dto
     * @author 강명관
     */
    public static AuthorizationHeaderDto getAuthHeaderDto() {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String memberNo = request.getHeader(HEADER_MEMBER_NO);
        String roleString = request.getHeader(HEADER_MEMBER_ROLE);

        checkInvalidRequest(memberNo, roleString);

        return new AuthorizationHeaderDto(
            Long.valueOf(memberNo),
            getRoleList(roleString)
        );
    }

    private static void checkInvalidRequest(String memberNo, String roleString) {
        if (Objects.isNull(memberNo) || Objects.isNull(roleString)) {
            throw new InvalidAuthRequestException();
        }
    }

    private static List<String> getRoleList(String roleString) {
        return Collections.list(new StringTokenizer(roleString, "[], ")).stream()
            .map(String::valueOf)
            .collect(Collectors.toList());
    }
}
