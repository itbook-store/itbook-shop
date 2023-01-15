package shop.itbook.itbookshop.deliverygroup.delivery.dto.response;

import lombok.Getter;

/**
 * 배송 등록 완료 후 배송 등록에 성공한 주문의 번호를 반환하는 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class DeliveryOrderNoResponseDto {

    private Long orderNo;
}
