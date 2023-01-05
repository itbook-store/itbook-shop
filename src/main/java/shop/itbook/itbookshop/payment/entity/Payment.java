package shop.itbook.itbookshop.payment.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 결제에 대한 엔티티 입니다.
 *
 * @author 이하늬
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_no", nullable = false)
    private Long paymentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_status_no", nullable = false, columnDefinition = "varchar(255)")
    private PaymentStatus paymentStatus;

    @Column(name = "order_no", nullable = false)
    private Long orderNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_no", nullable = false, columnDefinition = "varchar(20)")
    private Card card;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "payment_key", nullable = false, columnDefinition = "varchar(255)")
    private String paymentKey;

    @Column(name = "order_id", nullable = false, columnDefinition = "varchar(255)")
    private String orderId;

    @Column(name = "order_name", nullable = false, columnDefinition = "varchar(20)")
    private String orderName;

    @Column(name = "success_url", nullable = false, columnDefinition = "varchar(255)")
    private String successUrl;

    @Column(name = "fail_url", nullable = false, columnDefinition = "varchar(255)")
    private String failUrl;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "receipt_url", nullable = false, columnDefinition = "varchar(255)")
    private String receiptUrl;

    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt;

    @Column(name = "country", nullable = false, columnDefinition = "varchar(100)")
    private String country;

    @Column(name = "checkout_url", nullable = false, columnDefinition = "varchar(255)")
    private String checkoutUrl;

    @Column(name = "vat", nullable = false)
    private Long vat;
}
