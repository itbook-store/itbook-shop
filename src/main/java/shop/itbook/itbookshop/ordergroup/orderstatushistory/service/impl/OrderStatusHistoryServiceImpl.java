package shop.itbook.itbookshop.ordergroup.orderstatushistory.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * OrderStatusHistoryService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    private final OrderStatusService orderStatusService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addOrderStatusHistory(Order order, OrderStatusEnum orderStatusEnum) {

        //  TODO -> 여기서 Order 받고, 주문이 완료면 지금

        OrderStatus orderStatus = orderStatusService.findByOrderStatusEnum(orderStatusEnum);

        OrderStatusHistory orderStatusHistory =
            new OrderStatusHistory(order, orderStatus, LocalDateTime.now());
        orderStatusHistoryRepository.save(orderStatusHistory);
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistory orderStatusHistory) {
        return orderStatusHistoryRepository.save(orderStatusHistory);
    }
}
