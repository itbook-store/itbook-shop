package shop.itbook.itbookshop.coupongroup.categorycoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CategoryCouponRepository extends JpaRepository<CategoryCoupon, Long> ,CustomCategoryCouponRepository{
}
