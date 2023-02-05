package shop.itbook.itbookshop.deliverygroup.delivery.dummy;

import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 정재원
 * @since 1.0
 */
public class DeliveryDummy {

    public static Delivery createDelivery(Order order) {
        return new Delivery(order, "테스트 운송장 번호");
    }
}
