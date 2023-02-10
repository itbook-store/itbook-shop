package shop.itbook.itbookshop.paymentgroup.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 결제 승인 시 반환 받을 Payment 객체입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

    private Boolean success;
    private Integer code;
    private String message;
    private PaymentDataResponseDto data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentDataResponseDto {
        private String lastTransactionKey;
        private String paymentKey;
        private String orderId;
        private String orderName;
        private String status;
        private String requestedAt;
        private String approvedAt;
        private CardResponseDto card;
        private Receipt receipt;
        private Checkout checkout;
        private Cancels cancels;
        private String country;
        private Failure failure;
        private Long vat;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Receipt {
        private String url;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Checkout {
        private String url;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cancels {
        private String canceledAt;
        private Long cancelAmount;
        private String cancelReason;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Failure {
        private String code;
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardResponseDto {
        private String number;
        private Long amount;
        private String issuerCode;
        private String acquirerCode;
        private Integer installmentPlanMonths;
        private Boolean isInterestFree;
        private String approveNo;
        private Boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
    }
}



