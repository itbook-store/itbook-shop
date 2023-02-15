package shop.itbook.itbookshop.category.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class ParentCategoryNotFoundException extends RuntimeException {
    public static final String MESSAGE = "대분류 카테고리가 존재하지 않습니다.";

    public ParentCategoryNotFoundException() {
        super(MESSAGE);
    }
}
