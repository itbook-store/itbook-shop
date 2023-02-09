package shop.itbook.itbookshop.paymentgroup.payment.transfer;

import java.time.LocalDateTime;
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

        return Payment.builder()
//            .paymentStatus()
//            .order(requestDto)
//            .card(requestDto.getCard())
            .paymentKey(requestDto.getPaymentKey())
            .orderId(requestDto.getOrderId())
            .orderName(requestDto.getOrderName())
            .requestedAt(LocalDateTime.parse(requestDto.getRequestedAt()))
            .approvedAt(LocalDateTime.parse(requestDto.getApprovedAt()))
            .receiptUrl(requestDto.getReceipt().getUrl())
            .country(requestDto.getCountry())
            .checkoutUrl(requestDto.getCheckout().getUrl())
            .vat(requestDto.getVat())
            .build();
    }

}
