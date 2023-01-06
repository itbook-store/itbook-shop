package shop.itbook.itbookshop.coupon.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import shop.itbook.itbookshop.couponcoverage.entity.CouponCoverage;

/**
 * 쿠폰에 대한 엔터티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Coupon")
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no", nullable = false)
    private Long couponNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponCoverageNo")
    private CouponCoverage couponCoverage;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @Column(name = "amount", nullable = false)
    @ColumnDefault("0")
    private Long amount;

    @Column(name = "percent", nullable = false)
    @ColumnDefault("0")
    private Integer percent;

    @Column(name = "point", nullable = false)
    @ColumnDefault("0")
    private Long point;

    @Column(name = "standard_amount")
    private Long standardAmount;

    @Column(name = "max_discount_amount")
    private Long maxDiscountAmount;

    @Column(name = "coupon_created_at", nullable = false)
    private LocalDateTime couponCreatedAt;

    @Column(name = "coupon_expired_at", nullable = false)
    private LocalDateTime couponExpiredAt;

    @Column(name = "coupon_modified_at", nullable = false)
    private LocalDateTime couponModifiedAt;

    @Column(name = "image")
    private String image;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private String code;

    @Column(name = "is_reserved", nullable = false)
    @ColumnDefault("false")
    private boolean isReserved;

    /**
     * 퍼센트 쿠폰의 생성자입니다.
     *
     * @param couponCoverage    the coupon coverage
     * @param name              the name
     * @param percent           the percent
     * @param standardAmount    the standard amount
     * @param maxDiscountAmount the max discount amount
     * @param couponExpiredAt   the coupon expired at
     * @param couponModifiedAt  the coupon modified at
     * @param code              the code
     * @param isReserved        the is reserved
     * @author 송다혜
     */
    @Builder
    public Coupon(CouponCoverage couponCoverage, String name, Integer percent,
                  Long standardAmount, Long maxDiscountAmount, LocalDateTime couponExpiredAt,
                  LocalDateTime couponModifiedAt, String code, boolean isReserved) {
        this.couponCoverage = couponCoverage;
        this.name = name;
        this.percent = percent;
        this.standardAmount = standardAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.couponExpiredAt = couponExpiredAt;
        this.couponModifiedAt = couponModifiedAt;
        this.code = code;
        this.isReserved = isReserved;
    }

    /**
     * 정액쿠폰의 생성자 입니다.
     *
     * @param couponCoverage   the coupon coverage
     * @param name             the name
     * @param amount           the amount
     * @param standardAmount   the standard amount
     * @param couponExpiredAt  the coupon expired at
     * @param couponModifiedAt the coupon modified at
     * @param code             the code
     * @param isReserved       the is reserved
     * @author 송다혜
     */

    @Builder
    public Coupon(CouponCoverage couponCoverage, String name, Long amount,
                  Long standardAmount, LocalDateTime couponExpiredAt,
                  LocalDateTime couponModifiedAt, String code, boolean isReserved) {
        this.couponCoverage = couponCoverage;
        this.name = name;
        this.amount = amount;
        this.standardAmount = standardAmount;
        this.couponExpiredAt = couponExpiredAt;
        this.couponModifiedAt = couponModifiedAt;
        this.code = code;
        this.isReserved = isReserved;
    }

    /**
     * 포인트 쿠폰의 생성자입니다.
     *
     * @param couponCoverage   the coupon coverage
     * @param name             the name
     * @param point            the point
     * @param couponExpiredAt  the coupon expired at
     * @param couponModifiedAt the coupon modified at
     * @param code             the code
     * @param isReserved       the is reserved
     * @author 송다혜
     */
    @Builder
    public Coupon(CouponCoverage couponCoverage, String name,
                  Long point, LocalDateTime couponExpiredAt,
                  LocalDateTime couponModifiedAt, String code, boolean isReserved) {
        this.couponCoverage = couponCoverage;
        this.name = name;
        this.point = point;
        this.couponExpiredAt = couponExpiredAt;
        this.couponModifiedAt = couponModifiedAt;
        this.code = code;
        this.isReserved = isReserved;
    }
}

