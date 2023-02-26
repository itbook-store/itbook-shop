package shop.itbook.itbookshop.paymentgroup.card.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.paymentgroup.PaymentDummy;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.card.repository.CardRepository;
import shop.itbook.itbookshop.paymentgroup.card.service.CardService;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.paymentgroup.easypay.service.impl.EasypayServiceImpl;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CardServiceImpl.class)
class CardServiceImplTest {
    @Autowired
    CardService cardService;

    @MockBean
    CardRepository cardRepository;

    @Test
    void addCard() {
        Card card = PaymentDummy.getCard();

        PaymentResponseDto.PaymentDataResponseDto paymentResponseDto =
            new PaymentResponseDto.PaymentDataResponseDto();
        paymentResponseDto.setCard(
            new PaymentResponseDto.CardResponseDto(card.getCardSerialNo(), card.getTotalAmount(),
                card.getIssuerCode(), card.getAcquireCode(), card.getInstallmentPlanMonths(),
                card.isInterestFree(), card.getApproveNumber(), card.isUseCardPoint(),
                card.getType(), card.getOwnerType(), card.getAcquireStatus()));

        given(cardRepository.save(any(Card.class))).willReturn(card);
        Card actual = cardService.addCard(paymentResponseDto);

        Assertions.assertThat(actual).isEqualTo(card);
    }
}