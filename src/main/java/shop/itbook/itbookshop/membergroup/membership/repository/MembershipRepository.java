package shop.itbook.itbookshop.membergroup.membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * @author 강명관
 * @since 1.0
 */
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}
