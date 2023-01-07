package shop.itbook.itbookshop.advisor;

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

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<String> badRequestException400(RuntimeException e) {
        return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<String> internalErrorException500(Exception e) {
        return ResponseEntity.internalServerError().contentType(MediaType.TEXT_PLAIN)
            .body(e.getMessage());
    }

}
