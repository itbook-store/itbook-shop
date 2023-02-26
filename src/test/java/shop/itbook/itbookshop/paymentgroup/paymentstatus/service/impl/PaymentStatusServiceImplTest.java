package shop.itbook.itbookshop.paymentgroup.paymentstatus.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.paymentgroup.easypay.service.impl.EasypayServiceImpl;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.repository.PaymentStatusRepository;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.service.PaymentStatusService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PaymentStatusImpl.class)
class PaymentStatusImplTest {
    @Autowired
    PaymentStatusService paymentStatusService;

    @MockBean
    PaymentStatusRepository paymentStatusRepository;

    @Test
    void findPaymentStatusEntity() {
    }
}