package shop.itbook.itbookshop.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP를 통하여 요청 권한이 ADMIN 인지 체크하는 Annotation 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUser {
}
