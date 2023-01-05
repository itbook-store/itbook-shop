package shop.itbook.itbookshop.paymentstatusenum;

import lombok.Getter;

/**
 * 결제상태코드에 대한 enum입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public enum PaymentStatusEnum {
    READY("결제대기"), IN_PROGRESS("인증완료"), WAITING_FOR_DEPOSIT("입금대기"), DONE("결제승인"), CANCELED("결제취소"),
    PARTIAL_CANCELED("결제부분취소"), ABORTED("결제승인실패"), EXPIRED("결제유효시간만료");


    private String paymentStatus;

    PaymentStatusEnum(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
