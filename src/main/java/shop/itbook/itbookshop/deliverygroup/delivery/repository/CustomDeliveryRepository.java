package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;

/**
 * 쿼리 DSL 을 처리하기 위한 배송 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomDeliveryRepository {

    /**
     * 배송 정보를 배송 상태와 함께 불러옵니다.
     *
     * @return 배송 상태가 담긴 배송 정보의 리스트
     * @author 정재원 *
     */
    List<DeliveryWithStatusResponseDto> findDeliveryListWithStatus();

    /**
     * 배송 상태가 배송대기인 배송 정보의 리스트
     *
     * @return 배송 상태가 배송대기인 리스트
     * @author 정재원 *
     */
    List<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait();

    /**
     * 배송 상태가 배송대기인 배송 정보를 Entity 로 반환합니다.
     *
     * @return 배송 상태가 배송 대기인 배송 엔티티의 리스트
     * @author 정재원 *
     */
    List<Delivery> findDeliveryEntityListWithStatusWait();
}
