package shop.itbook.itbookshop.deliverygroup.deliverystatus.dummy;

import shop.itbook.itbookshop.deliverygroup.deliverystatus.DeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;

/**
 * @author 정재원
 * @since 1.0
 */
public class DeliveryStatusDummy {

    public static DeliveryStatus getDummyWait() {
        return new DeliveryStatus(1, DeliveryStatusEnum.WAIT_DELIVERY);
    }

    public static DeliveryStatus getDummyInProgress() {
        return new DeliveryStatus(2, DeliveryStatusEnum.DELIVERY_IN_PROGRESS);
    }

    public static DeliveryStatus getDummyCompleted() {
        return new DeliveryStatus(3, DeliveryStatusEnum.DELIVERY_COMPLETED);
    }
}
