package shop.itbook.itbookshop.advisor;

import javax.validation.ValidationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;

/**
 * rest controller 에서 예외발생시 종합적인 처리를 해주기 위한 클래스입니다.
 *
 * @author 최겸준
 * since 1.0
 */
@RestControllerAdvice
public class ControllerAdvisor {

    /**
     * 400에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {CategoryNotFoundException.class, ValidationException.class})
    public ResponseEntity<String> badRequestException400(RuntimeException e) {
        return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
    }

    /**
     * 500에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<String> internalErrorException500(Exception e) {
        return ResponseEntity.internalServerError().contentType(MediaType.TEXT_PLAIN)
            .body(e.getMessage());
    }

}
