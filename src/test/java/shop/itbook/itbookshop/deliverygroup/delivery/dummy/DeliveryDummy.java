package shop.itbook.itbookshop.deliverygroup.delivery.dummy;

import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;

/**
 * @author 정재원
 * @since 1.0
 */
public class DeliveryDummy {

    public static Delivery getDelivery() {
        return new Delivery(OrderDummy.getOrder(), "테스트 운송장 번호");
    }
}
