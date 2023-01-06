package shop.itbook.itbookshop.orderstatusenum;

import lombok.Getter;

/**
 * 주문 상태 Enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum OrderStatusEnum {

    WAITING_FOR_PAYMENT("결제대기중"),
    DEPOSIT_COMPLETE("입금완료"),
    DELIVERY_RECEPTION("배송접수중"),
    SHIPPING("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    REQUEST_EXCHANGE("교환신청중"),
    RETURN("반품신청중"),
    RETURN_COMPLETED("반품완료"),
    REQUEST_REFUND("환불신청중"),
    REFUND_COMPLETED("환불완료"),
    CANCELED("취소");

    private final String orderStatus;

    OrderStatusEnum(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
