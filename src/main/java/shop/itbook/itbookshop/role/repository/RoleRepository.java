package shop.itbook.itbookshop.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.role.entity.Role;

/**
 * @author 강명관
 * @since 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, CustomRoleRepository {
}
