package shop.itbook.itbookshop.productgroup.product.service.adminapi.impl.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;

/**
 * 배송 엔티티 Repository 의 테스트 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @MockBean
    DeliveryRepository deliveryRepository;

    @MockBean
    OrderRepository orderRepository;

    OrderDummy orderDummy;

    @BeforeEach
    void setup() {
        Member member = mock(Member.class);
        MemberDestination memberDestination = mock(MemberDestination.class);

    }

    @Test
    @DisplayName("delivery 테이블에 insert 성공")
    void insertTest() {
//        given(orderRepository.findById(1L)).willReturn(Optional.ofNullable(orderDummy));
//        Order order = orderRepository.findById(1L).get();
//
//        Delivery delivery = Delivery.builder()
//            .order(order)
//            .receiverName("테스트 수령인이름")
//            .receiverAddress("테스트 주소")
//            .receiverDetailAddress("테스트 상세주소")
//            .receiverPhoneNumber("테스트 전화번호")
//            .trackingNo("테스트 운송장번호")
//            .build();
//
//        given(deliveryRepository.save(delivery)).willReturn(delivery);
//
//        Delivery savedDelivery = deliveryRepository.save(delivery);
//
//        assertThat(savedDelivery.getReceiverName()).isEqualTo("테스트 수령인이름");
    }
}