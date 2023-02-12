package shop.itbook.itbookshop.coupongroup.couponissue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long>,
    CustomCouponIssueRepository {
}
