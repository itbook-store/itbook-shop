package shop.itbook.itbookshop.coupongroup.membershipcoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.entity.MembershipCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface MembershipCouponRepository extends JpaRepository<MembershipCoupon, Long> {
}
