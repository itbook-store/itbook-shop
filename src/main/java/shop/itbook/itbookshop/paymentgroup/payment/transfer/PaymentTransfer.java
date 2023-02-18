package shop.itbook.itbookshop.paymentgroup.payment.transfer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;

/**
 * 결제에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class PaymentTransfer {
    private PaymentTransfer() {
    }

    /**
     * dto로 넘어온 값을 상품 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param requestDto 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author
     */
    public static Payment dtoToEntity(PaymentResponseDto.PaymentDataResponseDto requestDto) {
        LocalDateTime requestedAt = LocalDateTime.from(
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(requestDto.getRequestedAt())).atZone(
                ZoneId.of("Asia/Seoul")));
        LocalDateTime approvedAt = LocalDateTime.from(
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(requestDto.getApprovedAt())).atZone(
                ZoneId.of("Asia/Seoul")));


        PaymentResponseDto.CardResponseDto card = requestDto.getCard();
        Long amount;
        if (Objects.isNull(card)) {
            amount = 0L;
        } else {
            amount = card.getAmount();
        }

        return Payment.builder()
            .paymentKey(requestDto.getPaymentKey())
            .orderId(requestDto.getOrderId())
            .totalAmount(amount)
            .orderName(requestDto.getOrderName())
            .requestedAt(requestedAt)
            .approvedAt(approvedAt)
            .receiptUrl(requestDto.getReceipt().getUrl())
            .country(requestDto.getCountry())
            .checkoutUrl(requestDto.getCheckout().getUrl())
            .vat(requestDto.getVat())
            .build();
    }

}
