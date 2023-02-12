package shop.itbook.itbookshop.ordergroup.order.transfer;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 관련 엔티티와 Dto 의 변환을 담당하는 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderTransfer {

    /**
     * 주문 테이블에 데이터 추가하는 요청 Dto 를 엔티티로 반환합니다.
     *
     * @param orderAddRequestDto 주문 요청 정보를 가진 Dto
     * @return DB에 저장될 새로운 주문 엔티티 인스턴스
     */
    public static Order addDtoToEntity(OrderAddRequestDto orderAddRequestDto) {
        return Order.builder()
            .orderCreatedAt(LocalDateTime.now())
            .selectedDeliveryDate(orderAddRequestDto.getSelectedDeliveryDate())
            .recipientName(orderAddRequestDto.getRecipientName())
            .recipientPhoneNumber(orderAddRequestDto.getRecipientPhoneNumber())
            .postcode(orderAddRequestDto.getPostcode())
            .roadNameAddress(orderAddRequestDto.getRoadNameAddress())
            .recipientAddressDetails(orderAddRequestDto.getRecipientAddressDetails())
            .isHidden(false)
            .build();
    }
}

