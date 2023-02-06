package shop.itbook.itbookshop.pointgroup.pointhistorychild.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.entity.ReviewIncreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface ReviewIncreasePointHistoryRepository
    extends JpaRepository<ReviewIncreasePointHistory, Long> {

}
