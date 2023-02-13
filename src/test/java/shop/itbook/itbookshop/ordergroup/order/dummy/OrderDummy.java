package shop.itbook.itbookshop.ordergroup.order.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            .selectedDeliveryDate(LocalDate.now())
            .recipientName("테스트 수령인 이름")
            .recipientPhoneNumber("010-test")
            .postcode(12345)
            .roadNameAddress("테스트 도로명주소")
            .recipientAddressDetails("테스트 상세주소")
            .isHidden(false)
            .increasePoint(0L)
            .decreasePoint(0L)
            .build();
    }
}
