package shop.itbook.itbookshop.paymentgroup.paymentcancel.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;

/**
 * 결제취소에 대한 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_cancel")
@Builder
@Entity
public class PaymentCancel {

    @Id
    private Long paymentNo;

    @MapsId("paymentNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_no", nullable = false)
    private Payment payment;

    @Column(name = "payment_cancel_created_at", nullable = false)
    private LocalDateTime paymentCancelCreatedAt;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "cancel_reason", nullable = false, columnDefinition = "varchar(255)")
    private String cancelReason;

    @Column(name = "refundable_amount", nullable = false)
    private Long refundableAmount;
}
