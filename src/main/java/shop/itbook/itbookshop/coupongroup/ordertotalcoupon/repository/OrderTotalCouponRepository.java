package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface OrderTotalCouponRepository extends JpaRepository<OrderTotalCoupon, Long>,
    CustomOrderTotalCouponRepository {
}
