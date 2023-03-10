package shop.itbook.itbookshop.paymentgroup.payment.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;

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
@Table(name = "payment",
    uniqueConstraints = {
        @UniqueConstraint(name = "UniquePaymentStatusNoAndOrderNo", columnNames = {
            "payment_status_no",
            "order_no"})})
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_no", nullable = false)
    private Long paymentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_status_no", nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_no")
    private Card card;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "easypay_no")
    private Easypay easypay;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    @Column(name = "receipt_url", nullable = false)
    private String receiptUrl;
    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "checkout_url", nullable = false)
    private String checkoutUrl;

    @Column(name = "vat", nullable = false)
    private Long vat;

    /**
     * 결제 엔티티 생성자입니다.
     *
     * @param paymentStatus the payment status
     * @param order         the order id
     * @param card          the card
     * @param totalAmount   the total amount
     * @param paymentKey    the payment key
     * @param orderId       the order no
     * @param orderName     the order name
     * @param receiptUrl    the receipt url
     * @param requestedAt   the requested at
     * @param approvedAt    the approved at
     * @param country       the country
     * @param checkoutUrl   the checkout url
     * @param vat           the vat
     * @author
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder

    public Payment(PaymentStatus paymentStatus, Order order, Card card, Easypay easypay,
                   Long totalAmount, String paymentKey, String orderId, String orderName,
                   String receiptUrl, LocalDateTime requestedAt, LocalDateTime approvedAt,
                   String country, String checkoutUrl, Long vat) {
        this.paymentStatus = paymentStatus;
        this.order = order;
        this.card = card;
        this.easypay = easypay;
        this.totalAmount = totalAmount;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.receiptUrl = receiptUrl;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.country = country;
        this.checkoutUrl = checkoutUrl;
        this.vat = vat;
    }
}
