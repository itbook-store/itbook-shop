package shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.dummy.DeliveryStatusHistoryDummy;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class DeliveryStatusHistoryRepositoryTest {

    @Autowired
    DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;

    @Disabled
    @Test
    @DisplayName("insert 성공 테스트")
    void insertTest() {
        DeliveryStatusHistory deliveryStatusHistory =
            DeliveryStatusHistoryDummy.getDeliveryStatusHistory();
        deliveryStatusHistory.setDeliveryStatusHistoryNo(33L);

//        deliveryStatusHistoryRepository.save(deliveryStatusHistory);

//        assertThat(
//            deliveryStatusHistoryRepository.findById(33L).get().getDeliveryStatus()).isEqualTo(
//            deliveryStatusHistory.getDeliveryStatus());
    }
}