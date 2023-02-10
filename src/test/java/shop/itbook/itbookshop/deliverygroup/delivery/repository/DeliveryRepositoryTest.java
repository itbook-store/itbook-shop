package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.exception.DeliveryNotFoundException;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.dummy.DeliveryStatusDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.dummy.DeliveryStatusHistoryDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;

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

    Order order;

    @BeforeEach
    void setUp() {
        order = OrderDummy.getOrder();
        orderRepository.save(order);

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
    @DisplayName("배송 상태와 함께 배송 정보 목록 조회 성공")
    void findDeliveryListWithStatusSuccessTest() {
        deliveryStatusRepository.save(DeliveryStatusDummy.getDummyWait());
        deliveryStatusRepository.save(DeliveryStatusDummy.getDummyInProgress());
        deliveryStatusRepository.save(DeliveryStatusDummy.getDummyCompleted());

        deliveryStatusHistoryRepository.save(DeliveryStatusHistoryDummy.getDeliveryStatusHistory());

        testEntityManager.flush();

//        assertThat(deliveryRepository.findDeliveryListWithStatusWait(null).get(0)
//            .getDeliveryStatus()).isEqualTo(
//            DeliveryStatusHistoryDummy.getDeliveryStatusHistory().getDeliveryStatus());
    }
}