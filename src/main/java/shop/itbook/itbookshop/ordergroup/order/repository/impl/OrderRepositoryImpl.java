package shop.itbook.itbookshop.ordergroup.order.repository.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.deliverygroup.deliverydestination.entity.QDeliveryDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.QMemberDestination;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaperResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.order.repository.CustomOrderRepository;

/**
 * CustomOrderRepository 인터페이스의 기능을 구현합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements
    CustomOrderRepository {

    public OrderRepositoryImpl() {
        super(Order.class);
    }


    @Override
    public OrderPaperResponseDto findOrderPaperInfo() {
        
        return null;
    }
}
