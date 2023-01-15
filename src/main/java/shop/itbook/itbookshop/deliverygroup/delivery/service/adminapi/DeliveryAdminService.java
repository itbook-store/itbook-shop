package shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryListRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryOrderNoResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 배송 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryAdminService {

    /**
     * 주문이 완료되었을 경우 배송 대기 상태로 배송 정보가 하나 추가 됩니다.
     *
     * @param order 완료된 주문 정보
     * @return 배송 정보 Pk 값의 번호
     * @author 정재원 *
     */
    DeliveryOrderNoResponseDto addDelivery(Order order);

    /**
     * 배송 관련 서비스 로직을 처리합니다.
     *
     * @param deliveryRequestDto 배송 서버에 등록을 요청할 Dto
     * @return 배송 서버로부터 받은 배송 정보
     * @throws JsonProcessingException Json 파싱 실패 시 나는 예외
     * @author 정재원 *
     */
    DeliveryDetailResponseDto sendDelivery(DeliveryServerRequestDto deliveryRequestDto);

    /**
     * 배송 엔티티의 리스트를 배송 상태 정보와 함께 반환합니다.
     *
     * @return the list
     * @author 정재원 *
     */
    List<DeliveryWithStatusResponseDto> findDeliveryListWithStatus();

    /**
     * 배송 엔티티의 리스트에서 배송 상태가 배송대기인 것만  반환합니다.
     *
     * @return the list
     * @author 정재원 *
     */
    List<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait();


    /**
     * 상태가 배송 대기인 배송 정보로 배송 더서버에 요청을 보냅니다.
     *
     * @return 배송 등록 성공한 배송 정보들의 리스트.
     * @author 정재원 *
     */
    List<DeliveryDetailResponseDto> sendDeliveryListWithStatusWait();

}
