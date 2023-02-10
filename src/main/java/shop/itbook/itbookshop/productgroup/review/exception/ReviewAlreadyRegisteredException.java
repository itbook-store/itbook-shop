package shop.itbook.itbookshop.productgroup.review.exception;

/**
 * 이미 리뷰가 등록된 주문 상품에 다시 등록하려할때 발생시키는 예외입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class ReviewAlreadyRegisteredException extends RuntimeException{

    public static final String MESSAGE = "이미 리뷰등록한 상품입니다.";

    public ReviewAlreadyRegisteredException() {
        super(MESSAGE);
    }
}
