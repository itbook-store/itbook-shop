package shop.itbook.itbookshop.productgroup.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.itbook.itbookshop.productgroup.product.exception.RequestValidationFailedException;


/**
 * 예외처리 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@RestControllerAdvice
@Slf4j
public class ProductControllerAdvice {


    @ExceptionHandler(RequestValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleApiException(RequestValidationFailedException e) {
        return ResponseEntity.badRequest()
            .body(e.getErrorMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.internalServerError()
            .body("system error");
    }
}
