package shop.itbook.itbookshop.paymentgroup.payment.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class PaymentRepositoryImplTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void findPaymentKeyWithOrderNo() {
    }

    @Test
    void findPaymentCardByOrderNo() {
    }
}