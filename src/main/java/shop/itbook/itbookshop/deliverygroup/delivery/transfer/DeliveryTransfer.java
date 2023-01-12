package shop.itbook.itbookshop.deliverygroup.delivery.transfer;

import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DummyServerDeliveryRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 배송 entity 와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class DeliveryTransfer {
    private DeliveryTransfer() {
    }

    /**
     * Delivery 엔티티를 응답을 위한 Dto 로 바꾸는 함수입니다.
     *
     * @param delivery dto 로 변경할 정보를 담은 배송 엔티티 입니다.
     * @return Delivery 엔티티의 모든 정보를 담은 DeliveryResponseDto 객체
     * @author 정재원 *
     */
    public static DeliveryResponseDto entityToDto(Delivery delivery) {

        return DeliveryResponseDto.builder()
            .deliveryNo(delivery.getDeliveryNo())
//            .orderNo(delivery.getOrder().getOrderNo())
            .trackingNo(delivery.getTrackingNo())
            .build();
    }

    /**
     * 주문 엔티티의 정보를 통해 배송 더미 서버에 요청할 RequestDto 를 만들어 냅니다.
     *
     * @param order 주문 완료시의 정보가 담긴 Order 객체
     * @return 배송 더미 서버에 요청할 필수 값이 담긴 RequestDto
     * @author 정재원 *
     */
    public static DummyServerDeliveryRequestDto orderEntityToDto(Order order) {
        return DummyServerDeliveryRequestDto.builder()
            .orderNo(order.getOrderNo())
            .receiverName(order.getMemberDestination().getRecipientName())
            .receiverAddress(order.getMemberDestination().getDeliveryDestination().getAddress())
            .receiverDetailAddress(order.getMemberDestination().getRecipientAddressDetails())
            .receiverPhoneNumber(order.getMemberDestination().getRecipientPhoneNumber())
            .build();
    }
}
