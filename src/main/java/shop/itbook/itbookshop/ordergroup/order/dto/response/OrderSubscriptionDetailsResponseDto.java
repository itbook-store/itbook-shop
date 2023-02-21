package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 주문 구독의 상세 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
public class OrderSubscriptionDetailsResponseDto {
    private Long orderNo;
    private Long orderProductNo;
    private String productName;
    private Integer count;
    private Long productPrice;
    private Long fixedPrice;
    private Double discountPercent;
    private Long sellingAmount;
    private String fileThumbnailsUrl;
    private OrderDestinationDto orderDestinationDto;
    private String orderStatus;
    private LocalDateTime orderCreatedAt;
    private Long amount;
    private Long deliveryFee;
    private Long deliveryNo;
    private LocalDate selectedDeliveryDate;
    private String trackingNo;

    // 주문 총액 쿠폰
    private String totalCouponName;
    private Long totalCouponAmount;
    private Integer totalCouponPercent;

    // 개별 적용 쿠폰
    private String couponName;
    private Long couponAmount;
    private Integer couponPercent;

    // 비회원
    private String nonMemberOrderCode;
}
