package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.exception.DeliveryNotFoundException;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.dummy.DeliveryStatusDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.dummy.DeliveryStatusHistoryDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * 배송 엔티티 Repository 의 테스트 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    DeliveryStatusRepository deliveryStatusRepository;
    @Autowired
    DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    OrderStatusHistoryRepository orderStatusHistoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrderProductRepository orderProductRepository;

    Order order;
    DeliveryWithStatusResponseDto deliveryWithStatusResponseDto;

    @BeforeEach
    void setUp() {
        order = OrderDummy.getOrder();
        orderRepository.save(order);

        // init dto
//        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryNo", 9999L);
//        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "orderNo", order.getOrderNo());
//        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "trackingNo", "12345678");
//        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryStatus",
//            DeliveryStatusEnum.WAIT_DELIVERY.getDeliveryStatus());

        // TODO: 2023/02/26 필요한거 setting 후 테스트.

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("delivery 테이블에 더미 데이터 insert 성공")
    void insertSuccessTest() {
        Delivery delivery = DeliveryDummy.createDelivery(order);

        Delivery savedDelivery = deliveryRepository.save(delivery);

        assertThat(savedDelivery.getTrackingNo()).isEqualTo(delivery.getTrackingNo());
    }

    @Test
    @DisplayName("delivery 테이블에서 조회 성공")
    void findSuccessTest() {
        Delivery delivery = DeliveryDummy.createDelivery(order);

        deliveryRepository.save(delivery);

        Delivery savedDelivery =
            deliveryRepository.findById(delivery.getDeliveryNo()).orElseThrow(
                DeliveryNotFoundException::new);

        assertThat(savedDelivery.getTrackingNo()).isEqualTo(delivery.getTrackingNo());
    }

    @Test
    @Disabled
    @DisplayName("결제 완료 상태의 리스트 조회 성공")
    void findDeliveryListWithStatusSuccessTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryStatus",
            OrderStatusEnum.PAYMENT_COMPLETE.getOrderStatus());

        Page<DeliveryWithStatusResponseDto> pageResponse =
            deliveryRepository.findDeliveryListWithStatus(pageRequest);

        List<DeliveryWithStatusResponseDto> deliveryWithStatusResponseDtoList =
            pageResponse.getContent();
    }
}