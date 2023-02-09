package shop.itbook.itbookshop.coupongroup.coupon.entity;

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
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;

/**
 * 쿠폰에 대한 엔터티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon")
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no", nullable = false)
    private Long couponNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @Column(name = "point", nullable = false)
    private Long point;

    @Column(name = "standard_amount")
    private Long standardAmount;

    @Column(name = "max_discount_amount")
    private Long maxDiscountAmount;

    @Column(name = "coupon_created_at", nullable = false)
    private LocalDateTime couponCreatedAt;

    @Column(name = "coupon_expired_at", nullable = false)
    private LocalDateTime couponExpiredAt;

    @Column(name = "coupon_modified_at")
    private LocalDateTime couponModifiedAt;

    @Column(name = "usage_period")
    private Integer usagePeriod;

    @Column(name = "image")
    private String image;

    @Setter
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "is_duplicate_use", nullable = false)
    private Boolean isDuplicateUse;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "issued_quantity", nullable = false)
    private Integer issuedQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_type_no", nullable = false)
    private CouponType couponType;



    /**
     * 쿠폰의 생성자 입니다.
     *
     * @param name              the name
     * @param amount            the amount
     * @param percent           the percent
     * @param point             the point
     * @param standardAmount    the standard amount
     * @param maxDiscountAmount the max discount amount
     * @param couponCreatedAt   the coupon created at
     * @param couponExpiredAt   the coupon expired at
     * @param couponModifiedAt  the coupon modified at
     * @param image             the image
     * @param code              the code
     * @param isDuplicateUse    the is duplicate use
     * @param totalQuantity     the total quantity
     * @param couponType        the coupon type
     * @author 송다혜
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public Coupon(String name, Long amount, Integer percent, Long point, Long standardAmount,
                  Long maxDiscountAmount, LocalDateTime couponCreatedAt,
                  LocalDateTime couponExpiredAt,
                  LocalDateTime couponModifiedAt, String image, String code, Boolean isDuplicateUse,
                  Integer totalQuantity, CouponType couponType) {
        this.name = name;
        this.amount = amount;
        this.percent = percent;
        this.point = point;
        this.standardAmount = standardAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.couponCreatedAt = couponCreatedAt;
        this.couponExpiredAt = couponExpiredAt;
        this.couponModifiedAt = couponModifiedAt;
        this.image = image;
        this.code = code;
        this.isDuplicateUse = isDuplicateUse;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = 0;
        this.couponType = couponType;
    }
}

