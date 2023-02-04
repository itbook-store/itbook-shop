package shop.itbook.itbookshop.ordergroup.orderproduct.repository.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.CustomOrderProductRepository;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;

/**
 * CustomOrderRepository 인터페이스의 기능을 구현합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderProductRepositoryImpl extends QuerydslRepositorySupport implements
    CustomOrderProductRepository {

    public OrderProductRepositoryImpl() {
        super(Order.class);
    }


    @Override
    public OrderSheetResponseDto findOrderPaperInfo() {

        return null;
    }
}
