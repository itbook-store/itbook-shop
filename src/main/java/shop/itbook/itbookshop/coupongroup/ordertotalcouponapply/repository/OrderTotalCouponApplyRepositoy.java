package shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface OrderTotalCouponApplyRepositoy extends JpaRepository<OrderTotalCouponApply, Long> {
    Optional<OrderTotalCouponApply> findByOrder_OrderNo(Long orderNo);
}
