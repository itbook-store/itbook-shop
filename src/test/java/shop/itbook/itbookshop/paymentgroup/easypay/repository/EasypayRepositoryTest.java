package shop.itbook.itbookshop.paymentgroup.easypay.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.paymentgroup.PaymentDummy;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class EasypayRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    EasypayRepository easypayRepository;


    @Test
    @DisplayName("easypay 객체 저장 성공 테스트")
    void save() {
        Easypay easypay = PaymentDummy.getEasypay();
        easypayRepository.save(easypay);

        entityManager.flush();
        entityManager.clear();

        Easypay actual = entityManager.find(Easypay.class, easypay.getEasypayNo());

        assertThat(actual.getEasypayNo()).isEqualTo(
            easypay.getEasypayNo());
    }

}