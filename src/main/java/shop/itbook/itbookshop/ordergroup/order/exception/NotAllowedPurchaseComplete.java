package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 강명관
 * @since 1.0
 */
public class NotAllowedPurchaseComplete extends RuntimeException {

    private static final String MESSAGE = "구매 확정 할 수 없는 주문입니다.";

    public NotAllowedPurchaseComplete() {
        super(MESSAGE);
    }
}
