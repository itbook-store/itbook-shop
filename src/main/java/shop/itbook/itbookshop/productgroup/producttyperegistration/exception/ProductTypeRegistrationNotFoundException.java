package shop.itbook.itbookshop.productgroup.producttyperegistration.exception;

/**
 * 상품유형 등록이 되어있지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductTypeRegistrationNotFoundException extends RuntimeException {

    public static final String MESSAGE = "상품유형 등록이 되어 있지 않습니다.";

    public ProductTypeRegistrationNotFoundException() {
        super(MESSAGE);
    }
}
