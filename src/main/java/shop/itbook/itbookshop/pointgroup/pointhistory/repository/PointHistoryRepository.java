package shop.itbook.itbookshop.pointgroup.pointhistory.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom.CustomPointHistoryRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>,
    CustomPointHistoryRepository {

    Optional<PointHistory> findFirstByMemberOrderByPointHistoryNoDesc(Member member);
}
