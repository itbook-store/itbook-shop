package shop.itbook.itbookshop.deliverygroup.deliverystatusenum;

import lombok.Getter;

/**
 * 배송 상태 Enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum DeliveryStatusEnum {
    WAIT_DELIVERY("배송대기"),
    DELIVERY_IN_PROGRESS("배송중"),
    DELIVERY_COMPLETED("배송완료");
    private final String deliveryStatus;

    DeliveryStatusEnum(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
