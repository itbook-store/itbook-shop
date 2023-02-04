package shop.itbook.itbookshop.ordergroup.ordermember.dummy;

import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;

/**
 * The type Order member dummy.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderMemberDummy {

    // 생성자에 더미 객체를 넣어 주세요
    public static OrderMember createOrderMember(Order order,
                                                MemberDestination memberDestination) {
        return new OrderMember(order, memberDestination);
    }
}