package shop.itbook.itbookshop.paymentgroup.card.transfer;

import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * 카드에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class CardTransfer {
    private CardTransfer() {
    }

    /**
     * dto로 넘어온 값을 상품 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param responseDto 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author
     */
    public static Card dtoToEntity(PaymentResponseDto.PaymentDataResponseDto responseDto) {
        PaymentResponseDto.CardResponseDto card = responseDto.getCard();
        return Card.builder()
            .cardSerialNo(card.getNumber())
            .totalAmount(card.getAmount())
            .issuerCode(card.getIssuerCode())
            .acquireCode(card.getAcquirerCode())
            .installmentPlanMonths(card.getInstallmentPlanMonths())
            .approveNumber(card.getApproveNo())
            .isUseCardPoint(card.getUseCardPoint())
            .type(card.getCardType())
            .ownerType(card.getOwnerType())
            .acquireStatus(card.getAcquireStatus())
            .isInterestFree(card.getIsInterestFree())
            .build();
    }

}
