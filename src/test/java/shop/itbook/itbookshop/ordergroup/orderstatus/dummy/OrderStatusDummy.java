package shop.itbook.itbookshop.ordergroup.orderstatus.dummy;

import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderStatusDummy {

    public static OrderStatus createByEnum(OrderStatusEnum orderStatusEnum) {
        return new OrderStatus(orderStatusEnum);
    }
}