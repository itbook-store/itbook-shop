package shop.itbook.itbookshop.role.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.role.dto.RoleResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomRoleRepository {

    Optional<RoleResponseDto> findByRoleName(String roleName);
}
