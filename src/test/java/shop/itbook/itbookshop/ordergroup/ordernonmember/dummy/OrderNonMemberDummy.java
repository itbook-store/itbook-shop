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
        return OrderNonMember.builder()
            .order(order)
            .recipientName("테스트 수령자")
            .recipientPhoneNumber("010-xxxx-test")
            .postcode(9999)
            .roadNameAddress("테스트 도로명 주소")
            .recipientAddressDetails("테스트 상세 주소")
            .nonMemberOrderCode(12345678L)
            .build();
//        return new OrderNonMember(order, "테스", "010", 123, "안녕", "ㅇㅇ", 123L);
    }
}