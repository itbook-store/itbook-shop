package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;

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
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("delivery 테이블에 더미 데이터 insert 성공")
    void insertTest() {
        Delivery delivery = DeliveryDummy.getDelivery();

        Delivery savedDelivery = deliveryRepository.save(delivery);

        assertThat(savedDelivery.getTrackingNo()).isEqualTo(delivery.getTrackingNo());
    }

    @Test
    @DisplayName("delivery 테이블에서 조회 성공")
    void findTest() {
        Delivery delivery = DeliveryDummy.getDelivery();

        deliveryRepository.save(delivery);

        Delivery savedDelivery =
            deliveryRepository.findById(delivery.getDeliveryNo()).orElseThrow(
                DeliveryNotFoundException::new);

        assertThat(savedDelivery.getTrackingNo()).isEqualTo(delivery.getTrackingNo());
    }

    @Disabled
    @Test
    @DisplayName("배송 상태와 함께 배송 정보 목록 조회 성공")
    void findDeliveryListWithStatusSuccessTest() {
        deliveryStatusRepository.save(DeliveryStatusDummy.getDummyWait());
        deliveryStatusRepository.save(DeliveryStatusDummy.getDummyInProgress());
        deliveryStatusRepository.save(DeliveryStatusDummy.getDummyCompleted());

//        deliveryStatusHistoryRepository.save(DeliveryStatusHistoryDummy.getDeliveryStatusHistory());

        testEntityManager.flush();
        testEntityManager.clear();

//        assertThat(deliveryRepository.findDeliveryListWithStatusWait().get(0)
//            .getDeliveryStatus()).isEqualTo(
//            DeliveryStatusHistoryDummy.getDeliveryStatusHistory().getDeliveryStatus());
    }
}