package shop.itbook.itbookshop.coupongroup.categorycouponapply.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface CategoryCouponApplyRepository extends JpaRepository<CategoryCouponApply, Long> {
    void deleteAllByCouponIssue_CouponIssueNo(Long couponIssueNo);

    Optional<CategoryCouponApply> findByOrderProduct_OrderProductNo(Long orderProductNo);
}
