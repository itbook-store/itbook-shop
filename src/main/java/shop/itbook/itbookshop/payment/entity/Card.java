package shop.itbook.itbookshop.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Builder
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_no", nullable = false)
    private Long cardNo;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "issuer_code", nullable = false, columnDefinition = "varchar(20)")
    private String issuerCode;

    @Column(name = "acquire_code", nullable = false, columnDefinition = "varchar(20)")
    private String acquireCode;

    @Column(name = "approve_number", nullable = false)
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

    @Column(name = "is_interest_free", nullable = false, columnDefinition = "varchar(20)")
    private boolean isInterestFree;
}
