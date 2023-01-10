package shop.itbook.itbookshop.productgroup.product.exception;

/**
 * 상품이 존재하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductNotFoundException extends RuntimeException {

    public static final String MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
