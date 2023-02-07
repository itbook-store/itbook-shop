package shop.itbook.itbookshop.ordergroup.orderstatusenum;

import java.util.Arrays;
import lombok.Getter;
import shop.itbook.itbookshop.ordergroup.orderstatus.exception.OrderStatusNotFoundException;

/**
 * 주문 상태 Enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum OrderStatusEnum {

    /**
     * Waiting for payment order status enum.
     */
    WAITING_FOR_PAYMENT("결제대기중"),
    /**
     * Deposit complete order status enum.
     */
    DEPOSIT_COMPLETE("입금완료"),
    /**
     * Delivery reception order status enum.
     */
    DELIVERY_RECEPTION("배송접수중"),
    /**
     * Shipping order status enum.
     */
    SHIPPING("배송중"),
    /**
     * Delivery completed order status enum.
     */
    DELIVERY_COMPLETED("배송완료"),
    /**
     * Request exchange order status enum.
     */
    REQUEST_EXCHANGE("교환신청중"),
    /**
     * Return order status enum.
     */
    RETURN("반품신청중"),
    /**
     * Return completed order status enum.
     */
    RETURN_COMPLETED("반품완료"),
    /**
     * Request refund order status enum.
     */
    REQUEST_REFUND("환불신청중"),
    /**
     * Refund completed order status enum.
     */
    REFUND_COMPLETED("환불완료"),
    /**
     * Purchase complete order status enum.
     */
    PURCHASE_COMPLETE("구매확정"),
    /**
     * Canceled order status enum.
     */
    CANCELED("취소");

    private final String orderStatus;

    OrderStatusEnum(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * DB 에서 불러온 한글 상태 값을 Enum 타입 인스턴스로 변환하기 위한 함수
     *
     * @param s 주문 상태의 한글 값
     * @return 해당 주문 상태의 Enum 타입 인스턴스
     */
    public static OrderStatusEnum stringToEnum(String s) {
        return Arrays.stream(OrderStatusEnum.values())
            .filter(value -> value.getOrderStatus().equals(s)).findFirst()
            .orElseThrow(() -> new OrderStatusNotFoundException(s));
    }
}