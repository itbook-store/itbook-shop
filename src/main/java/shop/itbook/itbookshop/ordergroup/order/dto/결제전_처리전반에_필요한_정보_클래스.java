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
public class 결제전_처리전반에_필요한_정보_클래스 {
    private OrderAddRequestDto orderAddRequestDto;
    private Long sequence;
    private Order order;

    private Long memberNo;

    private String orderType;

    public 결제전_처리전반에_필요한_정보_클래스(OrderAddRequestDto orderAddRequestDto, Long memberNo) {
        this.orderAddRequestDto = orderAddRequestDto;
        this.memberNo = memberNo;
    }

    public 결제전_처리전반에_필요한_정보_클래스(Long memberNo) {
        this.memberNo = memberNo;
    }
}
