package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * 주문 정보를 담은 응답용 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class OrderResponseDto {

    private Long orderNo;
    private Member memberNo;
    private MemberDestination memberDestination;
    private LocalDateTime orderCreatedAt;
    private boolean isSubscribed;
    private LocalDateTime countSpecifiedDeliveryDate;
}
