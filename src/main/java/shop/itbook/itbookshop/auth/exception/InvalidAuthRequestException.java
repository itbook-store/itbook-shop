package shop.itbook.itbookshop.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 인가된 회원 정보를 잘못 받아올 경우의 Exception 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAuthRequestException extends RuntimeException {

    private static final String MESSAGE = "잘못된 인가 정보 요청입니다.";

    public InvalidAuthRequestException() {
        super(MESSAGE);
    }
}
