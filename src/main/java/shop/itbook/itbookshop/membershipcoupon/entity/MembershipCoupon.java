package shop.itbook.itbookshop.membershipcoupon.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.coupon.entity.Coupon;
import shop.itbook.itbookshop.membership.entity.Membership;

/**
 * 이달의 쿠폰중 멤버쉽 쿠폰 엔터티 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membership_coupon")
public class MembershipCoupon {

    @Id
    private Long couponNo;

    @MapsId("couponNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_no")
    private Membership membership;

}