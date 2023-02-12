package shop.itbook.itbookshop.ordergroup.orderproduct.exception;

/**
 * 주문 상품 엔티티가 존재하지 않을 경우 발생하는 예외입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderProductNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 주문 상품 입니다.";

    public OrderProductNotFoundException() {
        super(MESSAGE);
    }
}
