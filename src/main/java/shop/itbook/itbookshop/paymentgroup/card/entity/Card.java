package shop.itbook.itbookshop.paymentgroup.card.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카드에 대한 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card")
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_no", nullable = false)
    private Long cardNo;

    @Column(name = "card_serial_no", nullable = false)
    private String cardSerialNo;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "issuer_code", nullable = false, columnDefinition = "varchar(20)")
    private String issuerCode;

    @Column(name = "acquire_code", nullable = false, columnDefinition = "varchar(20)")
    private String acquireCode;

    @Column(name = "installment_plan_months", nullable = false)
    private Integer installmentPlanMonths;

    @Column(name = "approve_number", nullable = false, columnDefinition = "varchar(20)")
    private String approveNumber;

    @Column(name = "is_use_card_point", nullable = false)
    private boolean isUseCardPoint;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(20)")
    private String type;

    @Column(name = "owner_type", nullable = false, columnDefinition = "varchar(20)")
    private String ownerType;

    @Column(name = "acquire_status", nullable = false, columnDefinition = "varchar(20)")
    private String acquireStatus;

    @Column(name = "is_interest_free", nullable = false)
    private boolean isInterestFree;

    /**
     * 카드 엔티티 생성자입니다.
     *
     * @param totalAmount           the total amount
     * @param issuerCode            the issuer code
     * @param acquireCode           the acquire code
     * @param installmentPlanMonths the installment plan months
     * @param approveNumber         the approve number
     * @param isUseCardPoint        the is use card point
     * @param type                  the type
     * @param ownerType             the owner type
     * @param acquireStatus         the acquire status
     * @param isInterestFree        the is interest free
     * @author
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public Card(String cardSerialNo, Long totalAmount, String issuerCode, String acquireCode,
                Integer installmentPlanMonths,
                String approveNumber, boolean isUseCardPoint, String type, String ownerType,
                String acquireStatus, boolean isInterestFree) {
        this.cardSerialNo = cardSerialNo;
        this.totalAmount = totalAmount;
        this.issuerCode = issuerCode;
        this.acquireCode = acquireCode;
        this.installmentPlanMonths = installmentPlanMonths;
        this.approveNumber = approveNumber;
        this.isUseCardPoint = isUseCardPoint;
        this.type = type;
        this.ownerType = ownerType;
        this.acquireStatus = acquireStatus;
        this.isInterestFree = isInterestFree;
    }
}
