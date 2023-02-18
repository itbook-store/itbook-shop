package shop.itbook.itbookshop.ordergroup.order.dto.response;

import lombok.Getter;

/**
 * 구독 주문 조회 페이지에서 사용되는 구독 주문 리스트를 갖고오는 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
public class OrderSubscriptionListDto {

    private Long orderNo;
    private String orderStatus;
    private String recipientName;
    private String recipientPhoneNumber;
    private String trackingNo;
    private Integer subscriptionPeriod;
}
