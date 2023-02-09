package shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderCancelIncreasePointHistoryRepository
    extends JpaRepository<OrderCancelIncreasePointHistory, Long> {

}
