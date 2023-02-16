package shop.itbook.itbookshop.paymentgroup.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카드 결제의 정보를 받아오기 위한 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class PaymentCardResponseDto {
    private String cardSerialNo;
    private String cardType;
    private Long totalAmount;
    private String paymentStatus;
}
