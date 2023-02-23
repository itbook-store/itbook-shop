package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;

/**
 * 주문 상세 조회를 위한 정보들을 담은 Dto 입니다.
 *
 * @author 정재원, 강명관
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponseDto {
    private Long orderNo;
    private LocalDateTime orderStatusCreatedAt;
    private String orderStatus;
    private LocalDateTime orderCreatedAt;
    private Long amount;
    private Long deliveryFee;
    private Long deliveryNo;
    private String trackingNo;
    private String couponName;
    private Long totalCouponAmount;
    private Integer totalCouponPercent;
    private OrderDestinationDto orderDestinationDto;
    @Setter
    private List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList;

    @Setter
    private Long sellingAmount;
    private String nonMemberOrderCode;
    @Setter
    private Long couponAmount;
}
