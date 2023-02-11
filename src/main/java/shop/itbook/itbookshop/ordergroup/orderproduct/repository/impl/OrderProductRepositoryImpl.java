package shop.itbook.itbookshop.ordergroup.orderproduct.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.CustomOrderProductRepository;

/**
 * CustomOrderRepository 인터페이스의 기능을 구현합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderProductRepositoryImpl extends QuerydslRepositorySupport implements
    CustomOrderProductRepository {

    public OrderProductRepositoryImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<OrderProductDetailResponseDto> findOrderProductsByOrderNo(Long orderNo) {

        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        return from(qOrderProduct)
            .where(qOrderProduct.order.orderNo.eq(orderNo))
            .select(Projections.fields(OrderProductDetailResponseDto.class,
                qOrderProduct.orderProductNo, qOrderProduct.product.name,
                qOrderProduct.count, qOrderProduct.productPrice))
            .fetch();
    }

}
