package shop.itbook.itbookshop.ordergroup.ordernonmember.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.dummy.OrderNonMemberDummy;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderNonMemberRepositoryTest {

    @Autowired
    OrderNonMemberRepository orderNonMemberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TestEntityManager testEntityManager;

    Order order;

    @BeforeEach
    void setUp() {
        // Order
        order = OrderDummy.getOrder();
        orderRepository.save(order);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("비회원의 주문정보 테이블에 save 성공")
    void saveSuccessTest() {

        OrderNonMember orderNonMember = OrderNonMemberDummy.createOrderNonMember(order);

        System.out.println(orderNonMember);

        OrderNonMember savedOrderNonMember = orderNonMemberRepository.save(orderNonMember);

        assertThat(savedOrderNonMember.getOrder().getOrderNo()).isEqualTo(
            orderNonMember.getOrder().getOrderNo());
    }

    @Test
    @DisplayName("주문 번호로 조회 성공")
    @Disabled
    void findByOrder_OrderNo() {
        OrderNonMember orderNonMember = OrderNonMemberDummy.createOrderNonMember(order);

        orderNonMemberRepository.save(orderNonMember);

        OrderNonMember savedOrderNonMember =
            orderNonMemberRepository.findByOrder_OrderNo(order.getOrderNo()).orElseThrow();

        assertThat(savedOrderNonMember.getOrder().getOrderNo()).isEqualTo(
            orderNonMember.getOrder().getOrderNo());
    }
}