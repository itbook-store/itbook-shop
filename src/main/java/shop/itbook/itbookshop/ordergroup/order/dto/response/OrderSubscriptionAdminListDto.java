package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
public class OrderSubscriptionAdminListDto {

    private Long orderNo;
    private String productName;
    private LocalDateTime orderCreatedAt;
    private String recipientName;
    private String orderStatus;
    private String memberId;
    private String trackingNo;
    private Integer subscriptionPeriod;
    
}
