package shop.itbook.itbookshop.ordergroup.orderstatushistory.service;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;

/**
 * 주문 상태 이력을 관련 로직을 담당하는 서비스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderStatusHistoryService {

    /**
     * 요청 받은 주문 상태로 주문 상태 이력을 등록합니다.
     *
     * @param order           상태를 등록할 주문 엔티티
     * @param orderStatusEnum 이력에 저장할 상태 정보 Enum
     * @author 정재원 *
     */
    void addOrderStatusHistory(Order order, OrderStatusEnum orderStatusEnum);

    OrderStatusHistory save(OrderStatusHistory orderStatusHistory);

    OrderStatusHistory findOrderStatusHistoryByOrderNo(Long orderNo);
}
