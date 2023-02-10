package shop.itbook.itbookshop.productgroup.product.exception;

/**
 * 상품이 팔 수 있는 상태가 아닐 경우 발생하는 예외입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class NotSellableProductException extends RuntimeException {

    public static final String MESSAGE = "팔 수 있는 상품이 아닙니다. 상품 번호: %d";

    public NotSellableProductException(Long productNo) {
        super(String.format(MESSAGE, productNo));
    }
}
