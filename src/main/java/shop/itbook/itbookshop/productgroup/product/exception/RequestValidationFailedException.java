package shop.itbook.itbookshop.productgroup.product.exception;

import lombok.Getter;

/**
 * 검증 실패 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public class RequestValidationFailedException extends RuntimeException {

    private final String errorMessage;

    public RequestValidationFailedException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }
}
