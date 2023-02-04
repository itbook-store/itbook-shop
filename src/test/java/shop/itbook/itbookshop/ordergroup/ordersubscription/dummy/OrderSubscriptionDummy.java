package shop.itbook.itbookshop.ordergroup.ordersubscription.dummy;

import static org.junit.jupiter.api.Assertions.*;

import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderSubscriptionDummy {
    public static OrderSubscription createOrderSubscription(Order order) {
        return new OrderSubscription(order, 12, 10);
    }
}