package shop.itbook.itbookshop.ordergroup.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePaymentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoForProcessOrderBeforePayment {
    private OrderAddRequestDto orderAddRequestDto;
    private Long sequence;
    private Order order;

    private Long memberNo;

    private String orderType;

    public InfoForProcessOrderBeforePayment(OrderAddRequestDto orderAddRequestDto, Long memberNo) {
        this.orderAddRequestDto = orderAddRequestDto;
        this.memberNo = memberNo;
    }

    public InfoForProcessOrderBeforePayment(Long memberNo) {
        this.memberNo = memberNo;
    }
}
