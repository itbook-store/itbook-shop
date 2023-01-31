package shop.itbook.itbookshop.role.repository.impl;

import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.role.entity.QRole;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.repository.CustomRoleRepository;

/**
 * @author 노수연
 * @since 1.0
 */
public class RoleRepositoryImpl extends QuerydslRepositorySupport implements CustomRoleRepository {


    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        QRole qRole = QRole.role;

        return Optional.of(
            from(qRole).select(qRole).where(qRole.roleType.stringValue().eq(roleName)).fetchOne());
    }
}
