package shop.itbook.itbookshop.membergroup.membershiphistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.membershiphistory.entity.MembershipHistory;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MembershipHistoryRepository
    extends JpaRepository<MembershipHistory, Long>, CustomMembershipHistoryRepository {
}
