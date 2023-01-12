package shop.itbook.itbookshop.productgroup.producttype.exception;

/**
 * 상품유형이 존재하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductTypeNotFoundException extends RuntimeException {

    public static final String MESSAGE = "존재하지 않는 상품유형입니다.";

    public ProductTypeNotFoundException() {
        super(MESSAGE);
    }
}
