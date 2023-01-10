package shop.itbook.itbookshop.membergroup.memberstatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusRepository extends JpaRepository<MemberStatus, Integer> {
}
