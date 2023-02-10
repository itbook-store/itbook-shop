package shop.itbook.itbookshop.coupongroup.productcouponapply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface ProductCouponApplyRepository extends JpaRepository<ProductCouponApply, Long> {
}
