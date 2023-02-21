package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * 비회원 주문 조회 시 잘못된 코드를 입력했을 경우 발생하는 예외
 *
 * @author 정재원
 * @since 1.0
 */
public class InvalidOrderCodeException extends RuntimeException {

    public static String MESSAGE = "잘못된 코드 입니다.";

    public InvalidOrderCodeException() {
        super(MESSAGE);
    }
}
