package shop.itbook.itbookshop.paymentgroup.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.card.repository.CardRepository;
import shop.itbook.itbookshop.paymentgroup.card.service.CardService;
import shop.itbook.itbookshop.paymentgroup.card.transfer.CardTransfer;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    @Transactional
    public Card addCard(PaymentResponseDto.PaymentDataResponseDto response) {
        Card card = CardTransfer.dtoToEntity(response);
        return cardRepository.save(card);
    }
}
