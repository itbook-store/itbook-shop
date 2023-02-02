package shop.itbook.itbookshop.pointgroup.pointhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

}
