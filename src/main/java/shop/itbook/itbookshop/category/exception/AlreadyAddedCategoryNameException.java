package shop.itbook.itbookshop.category.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class AlreadyAddedCategoryNameException extends RuntimeException {

    public static final String MESSAGE = "해당 메인카테고리의 자식카테고리에 이미 같은 이름의 카테고리가 존재합니다.";

    public AlreadyAddedCategoryNameException() {
        super(MESSAGE);
    }
}
