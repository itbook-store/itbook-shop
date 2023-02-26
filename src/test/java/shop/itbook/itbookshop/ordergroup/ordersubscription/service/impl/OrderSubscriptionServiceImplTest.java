package shop.itbook.itbookshop.ordergroup.ordersubscription.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.dummy.OrderSubscriptionDummy;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.service.OrderSubscriptionService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderSubscriptionServiceImpl.class)
class OrderSubscriptionServiceImplTest {

    @Autowired
    OrderSubscriptionService orderSubscriptionService;

    @MockBean
    OrderService orderService;

    @MockBean
    OrderSubscriptionRepository orderSubscriptionRepository;

    @MockBean
    OrderRepository orderRepository;

    Order dummyOrder;

    OrderSubscription dummyOrderSubscription;

    @BeforeEach
    void setUp() {
        dummyOrder = OrderDummy.getOrder();
        orderRepository.save(dummyOrder);

        dummyOrderSubscription = OrderSubscriptionDummy.createOrderSubscription(dummyOrder);
        orderSubscriptionRepository.save(dummyOrderSubscription);
    }

    @Test
    void registerOrderSubscription() {
        orderSubscriptionService.registerOrderSubscription(dummyOrder, 1);
    }

    @Test
    void isSubscription() {
        given(orderSubscriptionRepository.findByOrder_OrderNo(any())).willReturn(
            Optional.of(dummyOrderSubscription));

        assertThat(orderSubscriptionService.isSubscription(1L)).isTrue();
    }
}