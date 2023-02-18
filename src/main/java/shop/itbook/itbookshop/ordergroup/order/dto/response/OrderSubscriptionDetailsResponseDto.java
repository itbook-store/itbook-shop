package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 주문 구독의 상세 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderSubscriptionDetailsResponseDto {
    private Long orderNo;
    private Long orderProductNo;
    private String productName;
    private Integer count;
    private Long productPrice;
    private String fileThumbnailsUrl;
    private OrderDestinationDto orderDestinationDto;
    private String orderStatus;
    private LocalDateTime orderCreatedAt;
    private Long amount;
    private Long deliveryFee;
    private Long deliveryNo;
    private LocalDate selectedDeliveryDate;
    private String trackingNo;
}
