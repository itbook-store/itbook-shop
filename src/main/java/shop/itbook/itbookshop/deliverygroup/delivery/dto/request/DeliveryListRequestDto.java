package shop.itbook.itbookshop.deliverygroup.delivery.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 한꺼번에 여러 배송 등록 요청할 경우 여러 요청 객체를 담는 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class DeliveryListRequestDto {

    @NotNull(message = "배송 요청 객체 리스트는 Null 일 수 없습니다")
    List<DeliveryServerRequestDto> deliveryResponseDtoList;
}
