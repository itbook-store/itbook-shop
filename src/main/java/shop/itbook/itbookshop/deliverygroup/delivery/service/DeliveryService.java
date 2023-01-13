package shop.itbook.itbookshop.deliverygroup.delivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DummyServerDeliveryRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryResponseDto;

/**
 * 배송 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryService {
    /**
     * 배송 관련 서비스 로직을 처리합니다.
     *
     * @param deliveryRequestDto 배송 서버에 등록을 요청할 Dto
     * @return 배송 서버로부터 받은 배송 정보
     * @throws JsonProcessingException Json 파싱 실패 시 나는 예외
     * @author 정재원 *
     */
    DeliveryResponseDto addDelivery(DummyServerDeliveryRequestDto deliveryRequestDto)
        throws JsonProcessingException;
}
