package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentCardResponseDto;

/**
 * 주문 상세 조회를 위한 정보들을 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponseDto {
    private Long orderNo;
    private List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList;
    private OrderDestinationDto orderDestinationDto;
    private PaymentCardResponseDto paymentCardResponseDto;
    private String orderStatus;
    private LocalDateTime orderCreatedAt;
    private Long amount;
    private Long deliveryFee;
    private Long deliveryNo;
    private String trackingNo;

//    private String couponType;
//    private String couponName;
//    private LocalDateTime couponUsageCreatedAt;
//    private LocalDateTime couponIssueCreatedAt;
}
