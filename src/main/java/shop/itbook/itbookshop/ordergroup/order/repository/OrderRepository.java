package shop.itbook.itbookshop.ordergroup.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
