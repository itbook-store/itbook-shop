package shop.itbook.itbookshop.ordergroup.orderproducthistory.dummy;

import static org.junit.jupiter.api.Assertions.*;

import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderProductHistoryDummy {
    public static OrderProductHistory createOrderProductHistory(OrderProduct orderProduct,
                                                                OrderStatus orderStatus) {
        return new OrderProductHistory(orderProduct, orderStatus);
    }
}