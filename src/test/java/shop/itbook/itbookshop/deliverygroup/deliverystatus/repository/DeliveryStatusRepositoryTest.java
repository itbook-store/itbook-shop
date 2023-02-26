package shop.itbook.itbookshop.deliverygroup.deliverystatus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class DeliveryStatusRepositoryTest {

    @Autowired
    DeliveryStatusRepository deliveryStatusRepository;

    @Test
    @DisplayName("Enum 을 이용해서 배송 상태 테이블에서 배송 상태 엔티티 정보 가져오기 성공")
    void findByDeliveryStatusEnumSuccessTest() {

    }
}