package shop.itbook.itbookshop.ordergroup.orderproduct.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * 주문 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderProductRepository
    extends JpaRepository<OrderProduct, Long>, CustomOrderProductRepository {
    Optional<OrderProduct> findByOrder(Order order);

    List<OrderProduct> findByOrder_OrderNo(Long orderNo);
}
