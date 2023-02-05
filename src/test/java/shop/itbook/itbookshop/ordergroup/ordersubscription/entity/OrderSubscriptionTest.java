package shop.itbook.itbookshop.ordergroup.ordersubscription.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.dummy.OrderSubscriptionDummy;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderSubscriptionTest {
    @Autowired
    OrderSubscriptionRepository orderSubscriptionRepository;
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
    @DisplayName("주문 구독 엔티티 저장 성공")
    void saveSuccessTest() {
        OrderSubscription orderSubscription = OrderSubscriptionDummy.createOrderSubscription(order);

        OrderSubscription saveOrderSubscription =
            orderSubscriptionRepository.save(orderSubscription);

        assertThat(saveOrderSubscription.getSubscriptionPeriod()).isEqualTo(
            orderSubscription.getSubscriptionPeriod());
    }

    @Test
    @DisplayName("주문 구독 엔티티 Id로 검색 성공")
    void findSuccessTest() {
        OrderSubscription orderSubscription = OrderSubscriptionDummy.createOrderSubscription(order);

        orderSubscriptionRepository.save(orderSubscription);

        OrderSubscription findOrderSubscription =
            orderSubscriptionRepository.findById(order.getOrderNo()).orElseThrow();

        assertThat(findOrderSubscription.getSubscriptionPeriod()).isEqualTo(
            orderSubscription.getSubscriptionPeriod());
    }
}