package shop.itbook.itbookshop.ordergroup.orderproducthistory.repository;

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
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.dummy.OrderProductHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderProductHistoryRepositoryTest {

    @Autowired
    OrderProductHistoryRepository orderProductHistoryRepository;
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

    OrderProduct orderProduct;
    OrderStatus orderStatus;

    @BeforeEach
    void setUp() {
        Order order = orderRepository.save(OrderDummy.getOrder());
        Product product = productRepository.save(ProductDummy.getProductSuccess());
        orderProduct = OrderProductDummy.createOrderProduct(order, product);
        orderProductRepository.save(orderProduct);

        orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("주문 상품 이력 저장 성공")
    void saveSuccessTest() {
        OrderProductHistory orderProductHistory =
            OrderProductHistoryDummy.createOrderProductHistory(orderProduct, orderStatus);

        OrderProductHistory savedOrderProductHistory =
            orderProductHistoryRepository.save(orderProductHistory);

        assertThat(savedOrderProductHistory.getOrderProduct().getOrderProductNo()).isEqualTo(
            orderProductHistory.getOrderProduct().getOrderProductNo());
    }
}