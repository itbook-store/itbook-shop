package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;

/**
 * 배송 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
