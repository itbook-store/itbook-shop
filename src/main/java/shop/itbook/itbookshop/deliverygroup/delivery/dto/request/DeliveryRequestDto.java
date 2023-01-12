package shop.itbook.itbookshop.deliverygroup.delivery.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배송 생성 및 수정 요청시 정보를 보관할 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class DeliveryRequestDto {

    @NotNull(message = "주문 번호는 Null 일 수 없습니다.")
    private Long orderNo;

    @NotNull(message = "운송장번호는 null 일 수 없습니다.")
    private String trackingNo;
}
