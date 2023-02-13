package shop.itbook.itbookshop.productgroup.product.repository.impl;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.dummy.OrderStatusHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductSalesStatusRepositoryImplTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TestEntityManager entityManager;
    Pageable pageable;

    Product dummyProduct1;
    Product dummyProduct2;
    Product dummyProduct3;

    Order savedDummyOrder1;
    Order savedDummyOrder2;
    Order savedDummyOrder3;

    OrderStatus savedCanceledStatus;
    OrderStatus savedPurchaseCompletedStatus;
    OrderStatus savedRefundCompletedStatus;

    OrderStatus dummyOrderStatusCanceled;
    OrderStatus dummyOrderStatusPurchaseCompleted;
    OrderStatus dummyOrderStatusRefundCompleted;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, Integer.MAX_VALUE);

        dummyProduct1 = ProductDummy.getProductSuccess();
        dummyProduct2 = ProductDummy.getProductSuccess();
        dummyProduct3 = ProductDummy.getProductSuccess();

        Product savedDummyProduct1 = productRepository.save(dummyProduct1);
        Product savedDummyProduct2 = productRepository.save(dummyProduct2);
        Product savedDummyProduct3 = productRepository.save(dummyProduct3);

        Order dummyOrder1 = OrderDummy.getOrder();
        Order dummyOrder2 = OrderDummy.getOrder();
        Order dummyOrder3 = OrderDummy.getOrder();

        savedDummyOrder1 = orderRepository.save(dummyOrder1);
        savedDummyOrder2 = orderRepository.save(dummyOrder2);
        savedDummyOrder3 = orderRepository.save(dummyOrder3);

        OrderProduct dummyOrderProduct1 =
            OrderProductDummy.createOrderProduct(savedDummyOrder1, savedDummyProduct1);
        OrderProduct dummyOrderProduct2 =
            OrderProductDummy.createOrderProduct(savedDummyOrder1, savedDummyProduct3);
        OrderProduct dummyOrderProduct3 =
            OrderProductDummy.createOrderProduct(savedDummyOrder2, savedDummyProduct1);
        dummyOrderProduct3.setCount(4);
        OrderProduct dummyOrderProduct4 =
            OrderProductDummy.createOrderProduct(savedDummyOrder2, savedDummyProduct2);
        OrderProduct dummyOrderProduct5 =
            OrderProductDummy.createOrderProduct(savedDummyOrder3, savedDummyProduct1);
        OrderProduct dummyOrderProduct6 =
            OrderProductDummy.createOrderProduct(savedDummyOrder3, savedDummyProduct2);
        OrderProduct dummyOrderProduct7 =
            OrderProductDummy.createOrderProduct(savedDummyOrder3, savedDummyProduct3);

        OrderProduct savedDummyOrderProduct1 = orderProductRepository.save(dummyOrderProduct1);
        OrderProduct savedDummyOrderProduct2 = orderProductRepository.save(dummyOrderProduct2);
        OrderProduct savedDummyOrderProduct3 = orderProductRepository.save(dummyOrderProduct3);
        OrderProduct savedDummyOrderProduct4 = orderProductRepository.save(dummyOrderProduct4);
        OrderProduct savedDummyOrderProduct5 = orderProductRepository.save(dummyOrderProduct5);
        OrderProduct savedDummyOrderProduct6 = orderProductRepository.save(dummyOrderProduct6);
        OrderProduct savedDummyOrderProduct7 = orderProductRepository.save(dummyOrderProduct7);

        dummyOrderStatusCanceled = OrderStatusDummy.createByEnum(OrderStatusEnum.CANCELED);
        dummyOrderStatusPurchaseCompleted =
            OrderStatusDummy.createByEnum(OrderStatusEnum.PURCHASE_COMPLETE);
        dummyOrderStatusRefundCompleted =
            OrderStatusDummy.createByEnum(OrderStatusEnum.REFUND_COMPLETED);

        savedCanceledStatus = orderStatusRepository.save(dummyOrderStatusCanceled);
        savedPurchaseCompletedStatus =
            orderStatusRepository.save(dummyOrderStatusPurchaseCompleted);
        savedRefundCompletedStatus = orderStatusRepository.save(dummyOrderStatusRefundCompleted);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품 완료 건 수 순으로 조회 테스트")
    void Find_productList_orderByOrderCompleted() {

        OrderStatusHistory dummyOrderStatusHistory1 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder1, savedCanceledStatus);
        OrderStatusHistory dummyOrderStatusHistory2 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder2,
                savedPurchaseCompletedStatus);
        OrderStatusHistory dummyOrderStatusHistory3 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder3,
                savedPurchaseCompletedStatus);

        orderStatusHistoryRepository.save(dummyOrderStatusHistory1);
        orderStatusHistoryRepository.save(dummyOrderStatusHistory2);
        orderStatusHistoryRepository.save(dummyOrderStatusHistory3);

        List<ProductSalesRankResponseDto> productList =
            productRepository.findCompleteRankProducts(pageable).getContent();

        Assertions.assertThat(productList).hasSize(3);
        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(dummyProduct1.getProductNo());
        Assertions.assertThat(productList.get(0).getCount()).isEqualTo(5);
        Assertions.assertThat(productList.get(1).getProductNo())
            .isEqualTo(dummyProduct2.getProductNo());
        Assertions.assertThat(productList.get(1).getCount()).isEqualTo(2);
        Assertions.assertThat(productList.get(2).getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productList.get(2).getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 취소 건 수 순으로 조회 테스트")
    void Find_productList_orderByOrderCancled() {
        OrderStatusHistory dummyOrderStatusHistory1 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder1, savedCanceledStatus);
        OrderStatusHistory dummyOrderStatusHistory2 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder2,
                savedCanceledStatus);
        OrderStatusHistory dummyOrderStatusHistory3 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder3,
                savedPurchaseCompletedStatus);

        orderStatusHistoryRepository.save(dummyOrderStatusHistory1);
        orderStatusHistoryRepository.save(dummyOrderStatusHistory2);
        orderStatusHistoryRepository.save(dummyOrderStatusHistory3);

        List<ProductSalesRankResponseDto> productList =
            productRepository.findCanceledRankProducts(pageable).getContent();

        Assertions.assertThat(productList).hasSize(3);
        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(dummyProduct1.getProductNo());
        Assertions.assertThat(productList.get(0).getCount()).isEqualTo(5);
        Assertions.assertThat(productList.get(1).getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productList.get(1).getCount()).isEqualTo(1);
        Assertions.assertThat(productList.get(2).getProductNo())
            .isEqualTo(dummyProduct2.getProductNo());
        Assertions.assertThat(productList.get(2).getCount()).isEqualTo(1);
    }
}