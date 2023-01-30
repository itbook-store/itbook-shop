package shop.itbook.itbookshop.membergroup.memberstatushistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberstatushistory.entity.MemberStatusHistory;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusHistoryRepository extends JpaRepository<MemberStatusHistory, Long> {
}
