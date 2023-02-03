package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface GiftIncreaseDecreasePointHistoryRepository
    extends JpaRepository<GiftIncreaseDecreasePointHistory, Long> {

}
