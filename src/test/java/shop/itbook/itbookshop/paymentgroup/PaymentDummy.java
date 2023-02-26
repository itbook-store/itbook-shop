package shop.itbook.itbookshop.paymentgroup;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
public class PaymentDummy {

    public static Card getCard() {
        return Card.builder()
            .cardSerialNo("serialNo")
            .totalAmount(15000L)
            .issuerCode("issuer")
            .acquireCode("sdlkj")
            .installmentPlanMonths(3)
            .approveNumber("2134")
            .isUseCardPoint(false)
            .type("신용")
            .ownerType("owner")
            .acquireStatus("acquireStatus")
            .isInterestFree(false)
            .build();
    }

    public static Easypay getEasypay() {
        return new Easypay(null, "간편결제", 15000L, 0L);
    }

    public static PaymentCancel getPaymentCancel() {
        Payment payment = new Payment();

        LocalDateTime canceledAt = LocalDateTime.from(
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse("2022-01-01T11:32:04+09:00")).atZone(
                ZoneId.of("Asia/Seoul")));

        PaymentCancel paymentCancel = new PaymentCancel(canceledAt, 15000L, "단순변심");
        paymentCancel.setPayment(payment);
        paymentCancel.setPaymentCancelCreatedAt(canceledAt);

        return paymentCancel;
    }

    public static PaymentStatus getPaymentStatus() {
        return new PaymentStatus(1, PaymentStatusEnum.CANCELED);
    }

    public static Payment getPayment() {
        return Payment.builder()
            .paymentKey("paymentKey")
            .orderId("orderId")
            .totalAmount(15000L)
            .orderName("도서")
            .requestedAt(LocalDateTime.now())
            .approvedAt(LocalDateTime.now())
            .receiptUrl("url")
            .country("country")
            .checkoutUrl("url")
            .vat(0L)
            .build();
    }

}
