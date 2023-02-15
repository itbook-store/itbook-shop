package shop.itbook.itbookshop.productgroup.review.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class ReviewComCloseOtherMemberException extends RuntimeException {

    public static final String MESSAGE = "다른 회원의 리뷰에는 접근할 수 없습니다.";

    public ReviewComCloseOtherMemberException() {
        super(MESSAGE);
    }
}
