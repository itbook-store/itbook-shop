package shop.itbook.itbookshop.ordergroup.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 등록 성공 후 값을 반환하기 위한 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderAddResponseDto {
    private Long orderNo;
}
