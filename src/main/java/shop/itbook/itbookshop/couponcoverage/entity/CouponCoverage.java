package shop.itbook.itbookshop.couponcoverage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 쿠폰 사용 상태 관련 엔티티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon_coverage")
@Entity
public class CouponCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_coverage_no ", nullable = false)
    private Integer couponCoverageNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_name", nullable = false, columnDefinition = "varchar(20)")
    private CouponCoverageEnum coverageName;
}
