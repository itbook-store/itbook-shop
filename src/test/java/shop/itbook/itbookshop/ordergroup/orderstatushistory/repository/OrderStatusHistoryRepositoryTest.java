package shop.itbook.itbookshop.ordergroup.orderstatushistory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.dummy.OrderStatusHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderStatusHistoryRepositoryTest {

    @Autowired
    OrderStatusHistoryRepository orderStatusHistoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    TestEntityManager testEntityManager;

    Order order;
    OrderStatus orderStatus;

    @BeforeEach
    void setUp() {
        order = orderRepository.save(OrderDummy.getOrder());

        orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("주문 상태 이력 저장 성공")
    void saveSuccessTest() {
        OrderStatusHistory orderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);

        OrderStatusHistory savedOrderStatusHistory =
            orderStatusHistoryRepository.save(orderStatusHistory);

        assertThat(savedOrderStatusHistory.getOrderStatusHistoryNo()).isEqualTo(
            orderStatusHistory.getOrderStatusHistoryNo());
    }
}