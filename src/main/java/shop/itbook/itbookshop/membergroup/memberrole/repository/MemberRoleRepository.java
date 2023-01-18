package shop.itbook.itbookshop.membergroup.memberrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;

/**
 * 회원 권한 테이블에 대한 JPA Repository 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface MemberRoleRepository
    extends JpaRepository<MemberRole, MemberRole.Pk>, CustomMemberRoleRepository {
}
