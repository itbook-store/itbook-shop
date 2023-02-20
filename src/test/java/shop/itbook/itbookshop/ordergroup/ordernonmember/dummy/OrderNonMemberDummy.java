package shop.itbook.itbookshop.ordergroup.ordernonmember.dummy;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderNonMemberDummy {
    // 인자에 더미 객체를 넣어 주세요
    public static OrderNonMember createOrderNonMember(Order order) {

        return new OrderNonMember(order, "123L");
    }
}