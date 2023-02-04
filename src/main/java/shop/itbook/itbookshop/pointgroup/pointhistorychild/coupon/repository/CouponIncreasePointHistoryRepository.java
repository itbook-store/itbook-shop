package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface CouponIncreasePointHistoryRepository extends JpaRepository<CouponIncreasePointHistory, Long> {

}
