package shop.itbook.itbookshop.deliverygroup.deliverydestination.dummy;

import shop.itbook.itbookshop.deliverygroup.deliverydestination.entity.DeliveryDestination;

/**
 * @author 정재원
 * @since 1.0
 */
public class DeliveryDestinationDummy {

    public static DeliveryDestination getDeliveryDestination() {
        return new DeliveryDestination(1, 12345, "최초의 테스트 주소");
    }
}
