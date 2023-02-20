package shop.itbook.itbookshop.auth.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import shop.itbook.itbookshop.auth.exception.InvalidAuthRequestException;
import shop.itbook.itbookshop.common.exception.MemberForbiddenException;

/**
 * API 서버에 접근 권한을 검사하는 AOP 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private static final String TOKEN_HEADER_MEMBER_NO = "Authorization-MemberNo";

    private static final String TOKEN_HEADER_MEMBER_ROLE = "Authorization-MemberRole";

    private static final String ADMIN = "ADMIN";

    private static final String USER = "USER";

    /**
     * 관리자 권한이 필요한 API 메서드에 작동하는 AOP로
     * AccessToken의 권한을 검사하는 로직 입니다.
     *
     * @author 강명관
     */
    @Before("@annotation(shop.itbook.itbookshop.auth.annotation.IsAdmin)")
    public void authIsAdmin() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(requestAttributes)) {
            return;
        }

        HttpServletRequest request =
            ((ServletRequestAttributes) requestAttributes).getRequest();

        String tokenMemberRole = request.getHeader(TOKEN_HEADER_MEMBER_ROLE);

        List<String> roleList = parsingStringRoleToList(tokenMemberRole);

        if (!roleList.contains(ADMIN)) {
            throw new MemberForbiddenException();
        }

    }

    /**
     * 유저 권한이 필요한 API에 동작하는 AOP입니다.
     * PathVariable로 넘어온 memberNo 과 AccessToken에서 넘어온 값들을 비교하는 메서드 입니다.
     *
     * @author 강명관
     */
    @Before("@annotation(shop.itbook.itbookshop.auth.annotation.IsUser)")
    public void authIsUser() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return;
        }
        HttpServletRequest request =
            ((ServletRequestAttributes) requestAttributes).getRequest();

        String tokenMemberNo = request.getHeader(TOKEN_HEADER_MEMBER_NO);
        String tokenMemberRole = request.getHeader(TOKEN_HEADER_MEMBER_ROLE);


        checkValidHeader(tokenMemberNo, tokenMemberRole);

        List<String> roleList = parsingStringRoleToList(tokenMemberRole);

        if (roleList.contains(ADMIN)) {
            return;
        }

        if (!roleList.contains(USER)) {
            throw new InvalidAuthRequestException();
        }

        Map<String, String> pathVariables = (Map<String, String>) request
            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (!pathVariables.containsKey("memberNo")) {
            return;
        }

        if (!tokenMemberNo.equals(pathVariables.get("memberNo"))) {
            throw new InvalidAuthRequestException();
        }
    }

    private void checkValidHeader(String tokenMemberNo, String tokenMemberRole) {
        if (Objects.isNull(tokenMemberNo) || Objects.isNull(tokenMemberRole)) {
            throw new InvalidAuthRequestException();
        }
    }

    private List<String> parsingStringRoleToList(String roleString) {

        StringTokenizer st = new StringTokenizer(roleString, "[], ");
        List<String> roleList = new ArrayList<>();

        while (st.hasMoreTokens()) {
            roleList.add(st.nextToken());
        }
        return Collections.synchronizedList(roleList);
    }


}
