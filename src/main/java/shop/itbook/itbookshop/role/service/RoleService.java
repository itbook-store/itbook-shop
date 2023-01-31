package shop.itbook.itbookshop.role.service;

import shop.itbook.itbookshop.role.entity.Role;

/**
 * @author 노수연
 * @since 1.0
 */
public interface RoleService {

    Role findRole(String roleName);
}
