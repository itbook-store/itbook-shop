package shop.itbook.itbookshop.membergroup.memberrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;

/**
 * @author 강명관
 * @since 1.0
 */
public interface MemberRoleRepository
    extends JpaRepository<MemberRole, MemberRole.Pk>, CustomMemberRoleRepository {
}
