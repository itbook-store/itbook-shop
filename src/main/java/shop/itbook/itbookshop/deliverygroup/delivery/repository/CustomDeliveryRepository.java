package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @param pageable the pageable
     * @return 배송 상태가 담긴 배송 정보의 리스트
     * @author 정재원 *
     */
    Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatus(Pageable pageable);

    /**
     * 배송 상태가 배송대기인 배송 정보의 리스트
     *
     * @param pageable the pageable
     * @return 배송 상태가 배송대기인 리스트
     * @author 정재원 *
     */
    Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait(Pageable pageable);

    /**
     * 배송 상태가 배송대기인 배송 정보를 Entity 로 반환합니다.
     *
     * @return 배송 상태가 배송 대기인 배송 엔티티의 리스트
     * @author 정재원 *
     */
    List<Delivery> findDeliveryEntityListWithStatusWait();


    /**
     * 주문 번호로 운송장 번호를 조회합니다.
     *
     * @param orderNo 주문 번호
     * @return 해당 주문의 운송장 번호
     * @author 정재원 *
     */
    String findTrackingNoByOrderNo(Long orderNo);
}
