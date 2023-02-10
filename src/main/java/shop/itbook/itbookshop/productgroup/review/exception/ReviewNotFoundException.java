package shop.itbook.itbookshop.productgroup.review.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class ReviewNotFoundException extends RuntimeException {

    public static final String MESSAGE = "리뷰를 찾을 수 없습니다.";

    public ReviewNotFoundException() {
        super(MESSAGE);
    }
}
