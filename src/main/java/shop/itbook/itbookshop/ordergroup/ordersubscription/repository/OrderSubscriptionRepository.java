package shop.itbook.itbookshop.ordergroup.ordersubscription.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;

/**
 * 주문 구독 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderSubscriptionRepository extends JpaRepository<OrderSubscription, Long> {

    Optional<OrderSubscription> findByOrder_OrderNo(Long orderNo);
}
