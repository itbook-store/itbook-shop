package shop.itbook.itbookshop.ordergroup.orderstatushistory.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderStatusHistoryDummy {
    public static OrderStatusHistory createOrderStatusHistory(Order order,
                                                              OrderStatus orderStatus) {
        return new OrderStatusHistory(order, orderStatus, LocalDateTime.now());
    }
}