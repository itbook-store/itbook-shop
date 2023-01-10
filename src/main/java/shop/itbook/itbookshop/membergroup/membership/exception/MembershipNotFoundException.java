package shop.itbook.itbookshop.membergroup.membership.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 회원 등급을 찾지 못했을 경우의 404 Custom Excepiton 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MembershipNotFoundException extends RuntimeException {
}
