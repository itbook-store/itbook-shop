package shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;

/**
 * 배송 상태 이력 엔티티의 Repository
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryStatusHistoryRepository
    extends JpaRepository<DeliveryStatusHistory, Long> {
}
