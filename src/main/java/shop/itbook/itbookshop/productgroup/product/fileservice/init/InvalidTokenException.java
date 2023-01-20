package shop.itbook.itbookshop.productgroup.product.fileservice.init;

/**
 * @author 이하늬
 * @since 1.0
 */
public class InvalidTokenException extends RuntimeException {
    private static final String MESSAGE = "토큰이 유효하지 않습니다.";

    public InvalidTokenException() {
        super(MESSAGE);
    }
}
