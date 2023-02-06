package shop.itbook.itbookshop.coupongroup.productcoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long>,
    CustomProductCouponRepository{
}
