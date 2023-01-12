package shop.itbook.itbookshop.ordergroup.order.dummy;

import java.time.LocalDateTime;
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

        Order order = Order.builder()
            .member(MemberDummy.getMember())
            .memberDestination(MemberDestinationDummy.getMemberDestination())
            .isSubscribed(false)
            .build();

        order.setOrderNo(1L);

        return order;
    }
}