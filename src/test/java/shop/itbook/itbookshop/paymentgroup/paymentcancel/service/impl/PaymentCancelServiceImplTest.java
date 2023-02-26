package shop.itbook.itbookshop.paymentgroup.paymentcancel.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.paymentgroup.easypay.service.impl.EasypayServiceImpl;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.repository.PaymentCancelRepository;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.service.PaymentCancelService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PaymentCancelServiceImpl.class)
class PaymentCancelServiceImplTest {

    @Autowired
    PaymentCancelService paymentCancelService;

    @MockBean
    PaymentCancelRepository paymentCancelRepository;

    @Test
    void addPaymentCancel() {
        Payment payment = new Payment();

        LocalDateTime canceledAt = LocalDateTime.from(
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse("2022-01-01T11:32:04+09:00")).atZone(
                ZoneId.of("Asia/Seoul")));

        PaymentCancel paymentCancel = new PaymentCancel(canceledAt, 15000L, "단순변심");
        paymentCancel.setPayment(payment);
        paymentCancel.setPaymentCancelCreatedAt(canceledAt);
        PaymentResponseDto.Cancels[] cancels =
            {new PaymentResponseDto.Cancels("2022-01-01T11:32:04+09:00",
                paymentCancel.getAmount(), paymentCancel.getCancelReason())};
        PaymentResponseDto.PaymentDataResponseDto paymentResponseDto =
            new PaymentResponseDto.PaymentDataResponseDto();
        paymentResponseDto.setCancels(cancels);

        given(paymentCancelRepository.save(any(PaymentCancel.class))).willReturn(paymentCancel);
        PaymentCancel actual = paymentCancelService.addPaymentCancel(payment, paymentResponseDto);

        Assertions.assertThat(actual).isEqualTo(paymentCancel);
    }
}