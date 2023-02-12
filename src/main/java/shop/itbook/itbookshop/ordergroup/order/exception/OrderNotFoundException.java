package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * 주문 엔티티 조회 실패 시 발생하는 에러
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderNotFoundException extends RuntimeException {

    public static final String MESSAGE = "존재하지 않는 주문 정보입니다.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }

}
