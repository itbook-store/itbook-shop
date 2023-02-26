package shop.itbook.itbookshop.paymentgroup.paymentstatus.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.paymentgroup.PaymentDummy;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.exception.PaymentStatusNotFoundException;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.repository.PaymentStatusRepository;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.service.PaymentStatusService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PaymentStatusServiceImpl.class)
class PaymentStatusServiceImplTest {
    @Autowired
    PaymentStatusService paymentStatusService;

    @MockBean
    PaymentStatusRepository paymentStatusRepository;

    @Test
    @DisplayName("결제상태엔티티 조회가 잘 된다.")
    void findPaymentStatusEntity_success() {
        PaymentStatus paymentStatus = PaymentDummy.getPaymentStatus();
        given(paymentStatusRepository.findPaymentStatusByPaymentStatusEnum(any())).willReturn(
            Optional.of(paymentStatus));

        PaymentStatus actual =
            paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.CANCELED);

        Assertions.assertThat(actual.getPaymentStatusEnum())
            .isEqualTo(paymentStatus.getPaymentStatusEnum());

    }

    @Test
    @DisplayName("결제상태엔티티가 존재하지 않을 시 exception을 터뜨린다.")
    void findPaymentStatusEntity_failure() {
        given(paymentStatusRepository.findPaymentStatusByPaymentStatusEnum(any())).willReturn(
            Optional.empty());


        Assertions.assertThatThrownBy(
                () -> paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.CANCELED))
            .isInstanceOf(PaymentStatusNotFoundException.class)
            .hasMessage(PaymentStatusNotFoundException.MESSAGE);

    }
}