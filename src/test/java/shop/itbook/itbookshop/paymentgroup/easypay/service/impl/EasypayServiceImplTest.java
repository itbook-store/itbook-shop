package shop.itbook.itbookshop.paymentgroup.easypay.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.mysema.commons.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.service.impl.BookServiceImpl;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.paymentgroup.easypay.transfer.EasypayTransfer;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EasypayServiceImpl.class)
class EasypayServiceImplTest {

    @Autowired
    EasypayService easypayService;

    @MockBean
    EasypayRepository easypayRepository;

    @Test
    void addEasyPay() {
        Easypay easypay = new Easypay(1L, "간편결제", 15000L, 0L);

        PaymentResponseDto.PaymentDataResponseDto paymentResponseDto =
            new PaymentResponseDto.PaymentDataResponseDto();
        paymentResponseDto.setEasyPay(
            new PaymentResponseDto.EasyPayResponseDto(easypay.getProvider(), easypay.getAmount(),
                easypay.getDiscountAmount()));

        given(easypayRepository.save(any(Easypay.class))).willReturn(easypay);
        Easypay actual = easypayService.addEasyPay(paymentResponseDto);

        Assertions.assertThat(actual).isEqualTo(easypay);
    }
}