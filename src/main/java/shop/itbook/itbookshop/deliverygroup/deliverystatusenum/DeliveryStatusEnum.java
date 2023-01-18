package shop.itbook.itbookshop.deliverygroup.deliverystatusenum;

import java.util.Arrays;
import lombok.Getter;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.exception.DeliveryStatusNotFoundException;

/**
 * 배송 상태 Enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum DeliveryStatusEnum {

    /**
     * Wait delivery delivery status enum.
     */
    WAIT_DELIVERY("배송대기"),
    /**
     * Delivery in progress delivery status enum.
     */
    DELIVERY_IN_PROGRESS("배송중"),
    /**
     * Delivery completed delivery status enum.
     */
    DELIVERY_COMPLETED("배송완료");

    private final String deliveryStatus;

    DeliveryStatusEnum(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * 인자로 들어온 문자열 값을 가진 Enum 을 반환합니다.
     *
     * @param s Enum 에서 찾고자 하는 문자열
     * @return 찾았을 경우 해당 타입의 Enum 객체, 없을 경우 DeliveryStatusNotFoundException 예외 발생
     */
    public static DeliveryStatusEnum stringToEnum(String s) {
        return Arrays.stream(DeliveryStatusEnum.values())
            .filter(value -> value.getDeliveryStatus().equals(s)).findFirst().orElseThrow(
                DeliveryStatusNotFoundException::new);
    }
}
