package shop.itbook.itbookshop.common.advisor;

import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.common.exception.MemberForbiddenException;
import shop.itbook.itbookshop.common.response.CommonResponseBody;

/**
 * rest controller 에서 예외발생시 종합적인 처리를 해주기 위한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestControllerAdvice
public class RestControllerAdvisor {

    /**
     * 400에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e 실제 발생한 예외객체입니다.
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {CategoryNotFoundException.class, ValidationException.class})
    public ResponseEntity<CommonResponseBody<RuntimeException>> badRequestException400(
        RuntimeException e) {

        CommonResponseBody<RuntimeException> exceptionCommonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(false, HttpStatus.BAD_REQUEST.value(),
                e.getMessage()), null);

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
            .body(exceptionCommonResponseBody);
    }

    /**
     * 403에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e 실제 발생한 예외객체입니다.
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {MemberForbiddenException.class})
    public ResponseEntity<CommonResponseBody<RuntimeException>> forbiddenException403(
        RuntimeException e) {

        CommonResponseBody<RuntimeException> exceptionCommonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(false, HttpStatus.FORBIDDEN.value(),
                e.getMessage()), null);

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
            .body(exceptionCommonResponseBody);
    }

    /**
     * 500에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e 실제 발생한 예외객체입니다.
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<CommonResponseBody<Exception>> internalErrorException500(Exception e) {

        CommonResponseBody<Exception> exceptionCommonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()), null);

        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
            .body(exceptionCommonResponseBody);
    }

}
