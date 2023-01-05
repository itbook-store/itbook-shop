package shop.itbook.itbookshop.coupon.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

/**
 *쿠폰에 대한 엔터티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Coupon")
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no ", nullable = false)
    private Long couponNo;
    @Column(name = "coupon_coverage_no", nullable = false)
    private Integer couponCoverageNo;
    @Column(name = "name ", nullable = false, columnDefinition = "varchar(20)")
    private String name;
    @Column(name = "amount", nullable = false)
    @ColumnDefault("0")
    private Long amount;
    @Column(name = "percent ", nullable = false)
    @ColumnDefault("0")
    private Integer percent;
    @Column(name = "point", nullable = false)
    @ColumnDefault("0")
    private Long point;
    @Column(name = "standard_amount ")
    private Long standardAmount;
    @Column(name = "max_discount_amount")
    private Long maxDiscountAmount;
    @Column(name = "coupon_created_at ", nullable = false)
    private LocalDateTime couponCreatedAt;
    @Column(name = "coupon_expired_at", nullable = false)
    private LocalDateTime couponExpiredAt;
    @Column(name = "coupon_modified_at ", nullable = false)
    private LocalDateTime couponModifiedAt;
    @Column(name = "image")
    private String image;
    @Column(name = "code ", nullable = false, columnDefinition = "varchar(255)")
    private String code;
    @Column(name = "is_reserved ", nullable = false)
    @ColumnDefault("false")
    private boolean isReserved;
}

