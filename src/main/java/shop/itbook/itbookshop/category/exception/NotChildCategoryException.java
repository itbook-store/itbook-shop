package shop.itbook.itbookshop.category.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class NotChildCategoryException extends RuntimeException {

    public static final String MESSAGE = "부모 카테고리가 없는데도 불구하고 부모 카테고리가 필요한 요청을 했습니다.";

    public NotChildCategoryException() {
        super(MESSAGE);
    }
}
