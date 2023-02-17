package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class NotStatusOfOrderCancel extends RuntimeException {

    public static final String MESSAGE = "주문이 환불 가능한 상태가 아닙니다.";

    public NotStatusOfOrderCancel() {
        super(MESSAGE);
    }
}
