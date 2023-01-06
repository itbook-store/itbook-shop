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
    ORDER("주문"),
    DEPOSIT_COMPLETE("입금완료"),
    DELIVERY_RECEPTION("배송접수"),
    SHIPPING("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    EXCHANGE_REQUEST("교환신청"),
    RETURN("반품"),
    CANCEL("취소");
    private final String orderStatus;

    OrderStatusEnum(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
