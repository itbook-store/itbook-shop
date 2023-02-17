package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class OrderSubscriptionNotFirstSequenceException extends RuntimeException {

    public static final String MESSAGE = "잘못된 구독주문 취소요청입니다. 시퀀스는 첫번째여야 합니다.";

    public OrderSubscriptionNotFirstSequenceException() {
        super(MESSAGE);
    }
}
