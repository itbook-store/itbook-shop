package shop.itbook.itbookshop.paymentgroup.paymentcancel.transfer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;

/**
 * 결제취소에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class PaymentCancelTransfer {
    private PaymentCancelTransfer() {

    }

    /**
     * dto로 넘어온 값을 상품 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param response 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author
     */
    public static PaymentCancel dtoToEntity(PaymentResponseDto.PaymentDataResponseDto response) {

        PaymentResponseDto.Cancels cancels = response.getCancels()[0];

        LocalDateTime canceledAt = LocalDateTime.from(
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(cancels.getCanceledAt())).atZone(
                ZoneId.of("Asia/Seoul")));

        return new PaymentCancel(canceledAt, cancels.getCancelAmount(), cancels.getCancelReason());
    }

}
