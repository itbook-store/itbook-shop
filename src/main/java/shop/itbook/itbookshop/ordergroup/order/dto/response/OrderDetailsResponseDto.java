package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.util.List;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentCardResponseDto;

/**
 * 주문 상세 조회를 위한 정보들을 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Setter
public class OrderDetailsResponseDto {
    List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList;
    List<OrderDestinationDto> orderDestinationDtoList;
    PaymentCardResponseDto paymentCardResponseDto;
}
