//package shop.itbook.itbookshop.paymentgroup;
//
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import shop.itbook.itbookshop.paymentgroup.dto.request.PaymentRequestDto;
//import shop.itbook.itbookshop.paymentgroup.dto.response.PaymentResponseDto;
//
///**
// * @author 이하늬
// * @since 1.0
// */
//@Slf4j
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TossPayService.class)
//class TossPayServiceTest {
//
//    @Autowired
//    TossPayService tossPayService;
//
//    @Test
//    void responsePaymentTest_success() {
//        PaymentRequestDto requestDto =
//            new PaymentRequestDto("-7uzyWij0mxlNJJo2hxpr", 15000L, "Gz1n8igOcd_P6ReNzw_lv");
//        PaymentResponseDto responseDto =
//            tossPayService.requestApprovePayment(requestDto);
//
//        Assertions.assertThat(responseDto).isNotNull();
//        Assertions.assertThat(responseDto.getPaymentKey()).isEqualTo(requestDto.getPaymentKey());
//        Assertions.assertThat(responseDto.getStatus()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(responseDto.getCard().getAmount()).isEqualTo(requestDto.getAmount());
//    }
//}