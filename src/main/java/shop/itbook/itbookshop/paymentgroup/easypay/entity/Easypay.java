package shop.itbook.itbookshop.paymentgroup.easypay.entity;

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
@Table(name = "easypay")
@Entity
public class Easypay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "easypay_no", nullable = false)
    private Long easypayNo;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "discount_amount", nullable = false)
    private Long discountAmount;
}
