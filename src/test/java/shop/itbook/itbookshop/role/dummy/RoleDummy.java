package shop.itbook.itbookshop.role.dummy;

import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * @author 노수연
 * @since 1.0
 */
public class RoleDummy {

    public static Role getUserRole() {

        return new Role(RoleEnum.USER);
    }

    public static Role getAdminRole() {
        return new Role(RoleEnum.ADMIN);
    }
}
