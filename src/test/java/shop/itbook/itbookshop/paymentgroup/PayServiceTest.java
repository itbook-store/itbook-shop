//package shop.itbook.itbookshop.paymentgroup;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//
//import java.util.Base64;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.client.RestTemplate;
//import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
//import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
//import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
//import shop.itbook.itbookshop.paymentgroup.payment.service.PayService;
//import shop.itbook.itbookshop.paymentgroup.payment.service.impl.TossPayServiceImpl;
//
///**
// * @author 이하늬
// * @since 1.0
// */
//@Slf4j
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TossPayServiceImpl.class)
//class PayServiceTest {
//
//    @Autowired
//    PayService payService;
//
//    @MockBean
//    RestTemplate restTemplate;
//
//    PaymentResponseDto.PaymentDataResponseDto paymentDataResponseDto;
//
//    @BeforeEach
//    void setUp() {
//
//        PaymentResponseDto.CardResponseDto card =
//            new PaymentResponseDto.CardResponseDto("433012******1234", 15000L, "61", "31", 0, false,
//                "00000000", false, "신용", "개인",
//                "READY");
//
//        PaymentResponseDto.PaymentDataResponseDto paymentDataResponseDto =
//            new PaymentResponseDto.PaymentDataResponseDto("lastTransactionKey", "paymentKey",
//                "orderid",
//                "orderName", "status", "requestedAt", "approvedAt", card, null, null, null,
//                "country", null, 0L);
//
//    }
//
//    @Test
//    void responsePaymentTest_success() {
//        String TOSS_REQUEST_PAYMENT_URL =
//            "https://api.tosspayments.com/v1/payments/confirm";
//        PaymentApproveRequestDto requestDto =
//            new PaymentApproveRequestDto("-7uzyWij0mxlNJJo2hxpr", "orderId", 10000L);
//
//        String encodedSecretKey =
//            Base64.getEncoder().encodeToString(("${toss.payment.secret.key}" + ":").getBytes());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBasicAuth(encodedSecretKey);
//
//        HttpEntity<PaymentApproveRequestDto> httpEntity =
//            new HttpEntity<>(requestDto, headers);
//
//        given(restTemplate.exchange(anyString(), any(HttpMethod.class),
//            (HttpEntity<?>) any(HttpEntity.class), (Class<Object>) any())).willReturn(
//            paymentDataResponseDto);
//
//        PaymentResponseDto.PaymentDataResponseDto paymentDataResponseDto =
//            payService.requestApprovePayment(requestDto);
//
//        Assertions.assertThat(paymentDataResponseDto).isNotNull();
////        Assertions.assertThat(responseDto.getPaymentKey()).isEqualTo(requestDto.getPaymentKey());
////        Assertions.assertThat(responseDto.getStatus()).isEqualTo(HttpStatus.OK);
////        Assertions.assertThat(responseDto.getCard().getAmount()).isEqualTo(requestDto.getAmount());
//    }
//}