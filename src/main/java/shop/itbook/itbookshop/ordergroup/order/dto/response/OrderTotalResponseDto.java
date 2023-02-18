package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTotalResponseDto {

    private Long orderNo;
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
    
}
