package shop.itbook.itbookshop.ordergroup.orderproducthistory.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;

/**
 * 주문 상품 이력 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderProductHistoryRepository extends JpaRepository<OrderProductHistory, Long> {
    Optional<OrderProductHistory> findByOrderProduct_Order(Order order);
}
