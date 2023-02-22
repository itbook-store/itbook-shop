package shop.itbook.itbookshop.ordergroup.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

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
    private Order order;

    private Long memberNo;

    public InfoForPrePaymentProcess(OrderAddRequestDto orderAddRequestDto, Long memberNo) {
        this.orderAddRequestDto = orderAddRequestDto;
        this.memberNo = memberNo;
    }

    public InfoForPrePaymentProcess(OrderAddRequestDto orderAddRequestDto) {
        this.orderAddRequestDto = orderAddRequestDto;
    }
}
