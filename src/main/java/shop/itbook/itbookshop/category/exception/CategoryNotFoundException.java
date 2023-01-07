package shop.itbook.itbookshop.category.exception;

/**
 * 카테고리가 존재하지 않을때 발생시킬 예외 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryNotFoundException extends RuntimeException {

    public static final String MESSAGE = "카테고리가 존재하지 않습니다.";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }
}
