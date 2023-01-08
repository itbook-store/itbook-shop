package shop.itbook.itbookshop.deliverygroup.delivery.transfer;

import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;

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
            .orderNo(delivery.getOrder().getOrderNo())
            .receiverName(delivery.getReceiverName())
            .receiverAddress(delivery.getReceiverAddress())
            .receiverDetailAddress(delivery.getReceiverDetailAddress())
            .receiverPhoneNumber(delivery.getReceiverPhoneNumber())
            .trackingNo(delivery.getTrackingNo())
            .build();
    }
}
