package shop.itbook.itbookshop.paymentgroup.card.service;

import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface CardService {
    Card addCard(PaymentResponseDto.PaymentDataResponseDto response);
}
