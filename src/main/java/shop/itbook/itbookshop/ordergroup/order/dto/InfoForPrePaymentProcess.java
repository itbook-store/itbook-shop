package shop.itbook.itbookshop.ordergroup.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;

/**
 * @author 최겸준
 * @since 1.0
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoForPrePaymentProcess {
    private OrderAddRequestDto orderAddRequestDto;
    private Long sequence;
}
