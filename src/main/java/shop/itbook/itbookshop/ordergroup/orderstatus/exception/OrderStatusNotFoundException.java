package shop.itbook.itbookshop.ordergroup.orderstatus.exception;

import java.util.Arrays;

/**
 * The type Order status not found exception.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderStatusNotFoundException extends RuntimeException {

    public static final String MESSAGE = "해당 주문 상태를 찾지 못했습니다.";

    public OrderStatusNotFoundException() {
        super(MESSAGE);
    }

    /**
     * 찾으려는 주문 상태를 조회하지 못했을 경우 더 자세한 메세지를 알려줍니다.
     *
     * @param message 주문 상태를 찾기 위해 입력된 string 값
     */
    public OrderStatusNotFoundException(String message) {
        super(buildMessage(MESSAGE, " 입력된 상태 값: ", message));
    }

    private static String buildMessage(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(strings).forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}