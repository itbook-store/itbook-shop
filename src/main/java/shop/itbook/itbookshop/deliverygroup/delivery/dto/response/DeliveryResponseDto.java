package shop.itbook.itbookshop.deliverygroup.delivery.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Delivery 엔티티의 모든 필드를 반환하는 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Builder
public class DeliveryResponseDto {

    private Long deliveryNo;
    private Long orderNo;
    private String receiverName;
    private String receiverAddress;
    private String receiverDetailAddress;
    private String receiverPhoneNumber;
    private String trackingNo;
}
