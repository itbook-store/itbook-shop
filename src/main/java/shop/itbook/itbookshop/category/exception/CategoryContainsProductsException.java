package shop.itbook.itbookshop.category.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class CategoryContainsProductsException extends RuntimeException {

    public static final String MESSAGE = "카테고리에 상품이 포함되어 있어서 삭제가 불가합니다.";

    public CategoryContainsProductsException() {
        super(MESSAGE);
    }
}
