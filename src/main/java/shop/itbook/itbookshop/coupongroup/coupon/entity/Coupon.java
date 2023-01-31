package shop.itbook.itbookshop.coupongroup.coupon.entity;

import java.time.LocalDateTime;
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
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @Column(name = "amount", nullable = false, columnDefinition = "bigint default 0")
    private Long amount;

    @Column(name = "percent", nullable = false, columnDefinition = "integer default 0")
    private Integer percent;

    @Column(name = "point", nullable = false, columnDefinition = "bigint default 0")
    private Long point;

    @Column(name = "standard_amount")
    private Long standardAmount;

    @Column(name = "max_discount_amount")
    private Long maxDiscountAmount;

    @Column(name = "coupon_created_at", nullable = false,
        columnDefinition = "default now()")
    private LocalDateTime couponCreatedAt;

    @Column(name = "coupon_expired_at", nullable = false)
    private LocalDateTime couponExpiredAt;

    @Column(name = "coupon_modified_at")
    private LocalDateTime couponModifiedAt;

    @Column(name = "image", columnDefinition = "text")
    private String image;

    @Setter
    @Column(name = "code", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private String code;

    @Column(name = "is_reserved", nullable = false)
    private Boolean isReserved;

    @Builder
    public Coupon(String name, Long amount, Integer percent, Long point, Long standardAmount,
                  Long maxDiscountAmount, LocalDateTime couponCreatedAt,
                  LocalDateTime couponExpiredAt,
                  LocalDateTime couponModifiedAt, String image, String code, Boolean isReserved) {
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
        this.isReserved = isReserved;
    }
}

