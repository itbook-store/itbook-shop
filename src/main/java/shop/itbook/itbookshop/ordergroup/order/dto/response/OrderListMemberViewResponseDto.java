package shop.itbook.itbookshop.ordergroup.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 정보를 담은 응답용 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderListMemberViewResponseDto {

    private Long orderNo;
    private String orderStatus;
    private String recipientName;
    private String recipientPhoneNumber;
    @Setter
    private String trackingNo;
}
