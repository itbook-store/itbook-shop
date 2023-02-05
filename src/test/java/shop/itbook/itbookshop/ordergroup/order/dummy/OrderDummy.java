package shop.itbook.itbookshop.ordergroup.order.dummy;

import java.time.LocalDateTime;
import org.aspectj.weaver.ast.Or;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.dummy.MemberDestinationDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 테스트를 위한 Order 더미 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderDummy {
    public static Order getOrder() {
        return Order.builder()
            .orderCreatedAt(LocalDateTime.now())
            .selectedDeliveryDate(LocalDateTime.now())
            .recipientName("테스트 수령인 이름")
            .recipientPhoneNumber("010-test")
            .postcode(12345)
            .roadNameAddress("테스트 도로명주소")
            .recipientAddressDetails("테스트 상세주소")
            .build();
    }
}
