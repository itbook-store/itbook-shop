package shop.itbook.itbookshop.ordergroup.orderstatus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.exception.OrderStatusNotFoundException;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Override
    public OrderStatus findByOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        return orderStatusRepository.findByOrderStatusEnum(orderStatusEnum).orElseThrow(
            () -> new OrderStatusNotFoundException(orderStatusEnum.getOrderStatus()));
    }
}
