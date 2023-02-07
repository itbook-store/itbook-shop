package shop.itbook.itbookshop.ordergroup.orderstatus.service;

import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 정재원
 * @since 1.0
 */
public interface OrderStatusService {
    OrderStatus findByOrderStatusEnum(OrderStatusEnum orderStatusEnum);
}
