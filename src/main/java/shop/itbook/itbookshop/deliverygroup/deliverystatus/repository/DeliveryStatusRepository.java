package shop.itbook.itbookshop.deliverygroup.deliverystatus.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.entity.DeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;

/**
 * 배송 상태 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Integer> {

    Optional<DeliveryStatus> findByDeliveryStatusEnum(DeliveryStatusEnum deliveryStatusEnum);
}
