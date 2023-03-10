package shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;

/**
 * 배송 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryAdminService {

    /**
     * 배송 엔티티의 리스트를 배송 상태 정보와 함께 반환합니다.
     *
     * @return the list
     * @author 정재원 *
     */
    Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatus(Pageable pageable);

    /**
     * 배송 엔티티의 리스트에서 배송 상태가 배송대기인 것만  반환합니다.
     *
     * @return the list
     * @author 정재원 *
     */
    Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait(Pageable pageable);


    /**
     * 상태가 배송 대기인 배송 정보로 배송 더서버에 요청을 보냅니다.
     *
     * @return 배송 등록 성공한 배송 정보들의 리스트.
     * @author 정재원 *
     */
    List<DeliveryDetailResponseDto> sendDeliveryListWithStatusWait();

}
