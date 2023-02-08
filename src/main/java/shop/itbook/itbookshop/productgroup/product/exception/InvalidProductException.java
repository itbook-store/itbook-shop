package shop.itbook.itbookshop.productgroup.product.exception;


/**
 * 사용가 유효하지 않은 상품에 대해 접근할 경우의 Exception 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class InvalidProductException extends RuntimeException {

    private static final String MESSAGE = "유효하지 않은 상품 입니다.";

    public InvalidProductException() {
        super(MESSAGE);
    }
}
