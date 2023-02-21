package shop.itbook.itbookshop.productgroup.product.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
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
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductRepositoryTest {

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

    Product dummyProductSuccess1;
    Product dummyProductSuccess2;

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
    OrderStatus savedPaymentCompleted;

    OrderStatus dummyOrderStatusCanceled;
    OrderStatus dummyOrderStatusPurchaseCompleted;
    OrderStatus dummyOrderStatusPaymentCompleted;
    OrderStatus dummyOrderStatusRefundCompleted;
    OrderProduct dummyOrderProduct3;
    OrderProduct dummyOrderProduct4;
    OrderProduct dummyOrderProduct5;
    OrderProduct dummyOrderProduct6;
    OrderProduct dummyOrderProduct7;

    @BeforeEach
    void setUp() {
        dummyProductSuccess1 = ProductDummy.getProductSuccess();
        dummyProductSuccess2 = ProductDummy.getProductSuccess();
        dummyProductSuccess2.setName("객체지향의 거짓과 오해");

        productRepository.save(dummyProductSuccess1);
        productRepository.save(dummyProductSuccess2);

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
        dummyOrderProduct3 =
            OrderProductDummy.createOrderProduct(savedDummyOrder2, savedDummyProduct1);
        dummyOrderProduct3.setCount(4);
        dummyOrderProduct4 =
            OrderProductDummy.createOrderProduct(savedDummyOrder2, savedDummyProduct2);
        dummyOrderProduct5 =
            OrderProductDummy.createOrderProduct(savedDummyOrder3, savedDummyProduct1);
        dummyOrderProduct6 =
            OrderProductDummy.createOrderProduct(savedDummyOrder3, savedDummyProduct2);
        dummyOrderProduct7 =
            OrderProductDummy.createOrderProduct(savedDummyOrder3, savedDummyProduct3);

        orderProductRepository.save(dummyOrderProduct1);
        orderProductRepository.save(dummyOrderProduct2);
        orderProductRepository.save(dummyOrderProduct3);
        orderProductRepository.save(dummyOrderProduct4);
        orderProductRepository.save(dummyOrderProduct5);
        orderProductRepository.save(dummyOrderProduct6);
        orderProductRepository.save(dummyOrderProduct7);

        dummyOrderStatusCanceled = OrderStatusDummy.createByEnum(OrderStatusEnum.CANCELED);
        dummyOrderStatusPurchaseCompleted =
            OrderStatusDummy.createByEnum(OrderStatusEnum.PURCHASE_COMPLETE);
        dummyOrderStatusPaymentCompleted =
            OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        dummyOrderStatusRefundCompleted =
            OrderStatusDummy.createByEnum(OrderStatusEnum.REFUND_COMPLETED);

        savedCanceledStatus = orderStatusRepository.save(dummyOrderStatusCanceled);
        savedPurchaseCompletedStatus =
            orderStatusRepository.save(dummyOrderStatusPurchaseCompleted);
        savedRefundCompletedStatus = orderStatusRepository.save(dummyOrderStatusRefundCompleted);
        savedRefundCompletedStatus = orderStatusRepository.save(dummyOrderStatusRefundCompleted);
        savedPaymentCompleted = orderStatusRepository.save(dummyOrderStatusPaymentCompleted);


        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품 번호로 상품 조회 성공 테스트")
    void FindProductByProductNoTest() {

        Optional<Product> product =
            productRepository.findById(dummyProductSuccess1.getProductNo());

        Assertions.assertThat(product).isPresent();
        Assertions.assertThat(product.get().getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
    }

    @Test
    @DisplayName("상품 번호로 상품 상세정보 조회 성공 테스트")
    void FindProductDetailsByProductNoTest() {

        Optional<ProductDetailsResponseDto> product =
            productRepository.findProductDetails(dummyProductSuccess1.getProductNo());

        Assertions.assertThat(product).isPresent();
        Assertions.assertThat(product.get().getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void ModifyProductTest() {
        dummyProductSuccess1.setName("객체지향의 거짓과 오해");
        productRepository.save(dummyProductSuccess1);
        Assertions.assertThatNoException();
        Assertions.assertThat(dummyProductSuccess1.getName()).isEqualTo("객체지향의 거짓과 오해");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductByProductNoTest() {
        productRepository.deleteById(dummyProductSuccess1.getProductNo());
        Optional<Product> product =
            productRepository.findById(dummyProductSuccess1.getProductNo());
        Assertions.assertThat(product).isNotPresent();
    }

    @Test
    @DisplayName("<Admin> 모든 상품 리스트 조회 성공 테스트")
    void findProductListForAdminTest() {

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<ProductDetailsResponseDto> productList =
            productRepository.findProductListAdmin(pageable);
        Assertions.assertThat(productList).isNotEmpty();
        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.getContent().get(0);

        Assertions.assertThat(productList).hasSize(5);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsSelled())
            .isEqualTo(dummyProduct3.getIsSelled());
    }

    @Test
    @DisplayName("<User> 모든 상품 리스트 조회 성공 테스트")
    void findProductListForUserTest() {

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<ProductDetailsResponseDto> productList =
            productRepository.findProductListUser(pageable);
        Assertions.assertThat(productList).isNotEmpty();
        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.getContent().get(0);

        Assertions.assertThat(productList).hasSize(5);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsSelled())
            .isEqualTo(dummyProduct3.getIsSelled());
    }

    @Test
    @DisplayName("<User> 상품 번호 리스트로 상품 상세 리스트 조회 성공 테스트")
    void findProductListByProductNoListForUserTest() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Long> productNoList = new ArrayList<>();
        productNoList.add(dummyProductSuccess1.getProductNo());
        productNoList.add(dummyProductSuccess2.getProductNo());

        Page<ProductDetailsResponseDto> productList =
            productRepository.findProductListByProductNoListForUser(pageable, productNoList);
        Assertions.assertThat(productList).isNotEmpty();
        Assertions.assertThat(productList).hasSize(2);

        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.getContent().get(0);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsSelled())
            .isEqualTo(dummyProductSuccess1.getIsSelled());
    }

    @Test
    @DisplayName("<Admin> 상품 번호 리스트로 상품 상세 리스트 조회 성공 테스트")
    void findProductListByProductNoListForAdminTest() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Long> productNoList = new ArrayList<>();
        productNoList.add(dummyProductSuccess1.getProductNo());
        productNoList.add(dummyProductSuccess2.getProductNo());

        Page<ProductDetailsResponseDto> productList =
            productRepository.findProductListByProductNoListForAdmin(pageable, productNoList);
        Assertions.assertThat(productList).isNotEmpty();
        Assertions.assertThat(productList).hasSize(2);

        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.getContent().get(0);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsSelled())
            .isEqualTo(dummyProductSuccess1.getIsSelled());
    }

    @Test
    @DisplayName("상품 완료 건 수 순으로 조회 테스트")
    void Find_productList_orderByOrderCompleted() {

        OrderStatusHistory dummyOrderStatusHistory1 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder1, savedCanceledStatus);
        OrderStatusHistory dummyOrderStatusHistory2 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder2,
                savedPaymentCompleted);
        OrderStatusHistory dummyOrderStatusHistory3 =
            OrderStatusHistoryDummy.createOrderStatusHistory(savedDummyOrder3,
                savedPaymentCompleted);

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
            .isEqualTo(dummyProduct2.getProductNo());
        Assertions.assertThat(productList.get(1).getCount()).isEqualTo(1);
        Assertions.assertThat(productList.get(2).getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productList.get(2).getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("매출합계 순으로 조회 테스트")
    void Find_productList_orderByTotalAmount() {

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
            productRepository.findTotalSalesRankProducts(pageable).getContent();

        Assertions.assertThat(productList).hasSize(3);

        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(dummyProduct1.getProductNo());
        Assertions.assertThat(productList.get(0).getPrice())
            .isEqualTo(dummyOrderProduct4.getProductPrice() + dummyOrderProduct6.getProductPrice());

        Assertions.assertThat(productList.get(1).getProductNo())
            .isEqualTo(dummyProduct2.getProductNo());
        Assertions.assertThat(productList.get(1).getPrice())
            .isEqualTo(dummyOrderProduct3.getProductPrice() + dummyOrderProduct5.getProductPrice());

        Assertions.assertThat(productList.get(2).getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productList.get(2).getPrice())
            .isEqualTo(dummyOrderProduct7.getProductPrice());
    }

    @Test
    @DisplayName("판매금액 순으로 조회 테스트")
    void Find_productList_orderBySalesPrice() {

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
            productRepository.findSelledPriceRankProducts(pageable).getContent();

        Assertions.assertThat(productList).hasSize(3);

        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(dummyProduct1.getProductNo());
        Assertions.assertThat(productList.get(0).getPrice())
            .isEqualTo(
                Double.valueOf(dummyProduct1.getFixedPrice() *
                    ((100 - dummyProduct1.getDiscountPercent()) * 0.01)
                    * productList.get(0).getCount()).longValue());

        Assertions.assertThat(productList.get(1).getProductNo())
            .isEqualTo(dummyProduct2.getProductNo());
        Assertions.assertThat(productList.get(1).getPrice())
            .isEqualTo(
                Double.valueOf(dummyProduct2.getFixedPrice() *
                    ((100 - dummyProduct2.getDiscountPercent()) * 0.01)
                    * productList.get(1).getCount()).longValue());

        Assertions.assertThat(productList.get(2).getProductNo())
            .isEqualTo(dummyProduct3.getProductNo());
        Assertions.assertThat(productList.get(2).getPrice())
            .isEqualTo(
                Double.valueOf(dummyProduct3.getFixedPrice() *
                    ((100 - dummyProduct3.getDiscountPercent()) * 0.01)
                    * productList.get(2).getCount()).longValue());
    }
}