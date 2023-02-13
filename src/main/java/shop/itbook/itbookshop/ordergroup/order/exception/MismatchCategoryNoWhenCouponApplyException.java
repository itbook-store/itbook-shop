package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class MismatchCategoryNoWhenCouponApplyException extends RuntimeException {

    public static final String MESSAGE = "요청된 카테고리 쿠폰이 실제 등록된 상품의 카테고리에 포함되지 않습니다.";

    public MismatchCategoryNoWhenCouponApplyException() {
        super(MESSAGE);
    }
}
