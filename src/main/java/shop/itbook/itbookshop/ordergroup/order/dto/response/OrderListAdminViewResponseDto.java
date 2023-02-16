package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 관리자의 주문 목록 조회시 보여줄 정보 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class OrderListAdminViewResponseDto {

    private Long orderNo;
    private String productName;
    private LocalDateTime orderCreatedAt;
    private String recipientName;
    private String orderStatus;
    private String memberId;
    private String trackingNo;
}