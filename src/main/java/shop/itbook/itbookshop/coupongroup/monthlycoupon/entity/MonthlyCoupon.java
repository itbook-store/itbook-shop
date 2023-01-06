package shop.itbook.itbookshop.coupongroup.monthlycoupon.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.entity.MembershipCoupon;

/**
 * 예약형 이달의 쿠폰 관련 테이블 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "monthly_coupon")
public class MonthlyCoupon {

    @Id
    private Long couponNo;

    @MapsId("couponNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private MembershipCoupon membershipCoupon;

    @Column(name = "coupon_reservation_created_at", nullable = false,
        columnDefinition = "default now()")
    private LocalDateTime couponReservationCreatedAt;
}
