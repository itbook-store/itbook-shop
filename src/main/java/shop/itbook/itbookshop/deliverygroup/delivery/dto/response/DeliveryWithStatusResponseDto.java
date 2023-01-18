package shop.itbook.itbookshop.deliverygroup.delivery.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송 엔티티의 값과 배송 상태까지 나타내는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class DeliveryWithStatusResponseDto {

    private Long deliveryNo;
    private Long orderNo;
    private String trackingNo;
    private String deliveryStatus;
}
