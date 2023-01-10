package shop.itbook.itbookshop.membergroup.membership.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}
