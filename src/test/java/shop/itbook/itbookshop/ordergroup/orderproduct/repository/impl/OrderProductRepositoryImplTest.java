package shop.itbook.itbookshop.ordergroup.orderproduct.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class OrderProductRepositoryImplTest {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    OrderProduct dummyOrderProduct;

    Order dummyOrder;

    Product dummyProduct;

    @BeforeEach
    void setUp() {
        dummyOrder = OrderDummy.getOrder();
        orderRepository.save(dummyOrder);

        dummyProduct = ProductDummy.getProductSuccess();
        productRepository.save(dummyProduct);

        dummyOrderProduct = OrderProductDummy.createOrderProduct(dummyOrder, dummyProduct);
        orderProductRepository.save(dummyOrderProduct);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findOrderProductsByOrderNo() {
        List<OrderProductDetailResponseDto> orderProductList =
            orderProductRepository.findOrderProductsByOrderNo(
                dummyOrderProduct.getOrder().getOrderNo());

        assertThat(orderProductList.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Optional<OrderProduct> orderProduct = orderProductRepository.findById(
            dummyOrderProduct.getOrderProductNo());

        assertThat(orderProduct).isPresent();
        assertThat(orderProduct.get().getOrderProductNo())
            .isEqualTo(dummyOrderProduct.getOrderProductNo());
    }
}