package shop.itbook.itbookshop.deliverygroup.delivery.transfer;

import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
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
    public static DeliveryDetailResponseDto entityToDetailDto(Delivery delivery) {

        return DeliveryDetailResponseDto.builder()
            .deliveryNo(delivery.getDeliveryNo())
            .orderNo(delivery.getOrder().getOrderNo())
            .trackingNo(delivery.getTrackingNo())
            .build();
    }

    /**
     * 배송 엔티티를 배송 서버에 요청할 Dto 로 변환합니다.
     *
     * @return 배송 서버에 요청할 정보가 담긴 Dto
     * @author 정재원 *
     */
    public static DeliveryServerRequestDto entityToServerRequestDto(Delivery delivery) {
        return DeliveryServerRequestDto.builder()
            .orderNo(delivery.getOrder().getOrderNo())
            .receiverName(delivery.getOrder().getRecipientName())
            .receiverAddress(
                delivery.getOrder().getRoadNameAddress())
            .receiverDetailAddress(
                delivery.getOrder().getRecipientAddressDetails())
            .receiverPhoneNumber(
                delivery.getOrder().getRecipientPhoneNumber())
            .build();
    }

    /**
     * 주문 엔티티의 정보를 통해 배송 더미 서버에 요청할 RequestDto 를 만들어 냅니다.
     *
     * @param order 주문 완료시의 정보가 담긴 Order 객체
     * @return 배송 더미 서버에 요청할 필수 값이 담긴 RequestDto
     * @author 정재원 *
     */
    public static DeliveryServerRequestDto orderEntityToDto(Order order) {
        return DeliveryServerRequestDto.builder()
            .orderNo(order.getOrderNo())
            .receiverName(order.getRecipientName())
            .receiverAddress(
                order.getRoadNameAddress())
            .receiverDetailAddress(
                order.getRecipientAddressDetails())
            .receiverPhoneNumber(
                order.getRecipientPhoneNumber())
            .build();
    }
}
