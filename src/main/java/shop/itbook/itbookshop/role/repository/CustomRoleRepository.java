package shop.itbook.itbookshop.role.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.role.entity.Role;

/**
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomRoleRepository {

    Optional<Role> findByRoleName(String roleName);
}
