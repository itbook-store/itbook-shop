package shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.entity.GradeIncreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface GradeIncreasePointHistoryRepository
    extends JpaRepository<GradeIncreasePointHistory, Long> {

}
