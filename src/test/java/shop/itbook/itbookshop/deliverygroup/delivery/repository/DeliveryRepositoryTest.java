package shop.itbook.itbookshop.deliverygroup.delivery.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.exception.DeliveryNotFoundException;

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
}