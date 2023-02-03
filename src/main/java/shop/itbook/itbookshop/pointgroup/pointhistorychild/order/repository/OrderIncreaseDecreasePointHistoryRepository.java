package shop.itbook.itbookshop.pointgroup.pointhistorychild.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.OrderIncreaseDecreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderIncreaseDecreasePointHistoryRepository
    extends JpaRepository<OrderIncreaseDecreasePointHistory, Long> {

}
