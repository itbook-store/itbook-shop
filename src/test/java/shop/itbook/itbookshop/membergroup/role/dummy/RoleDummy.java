package shop.itbook.itbookshop.membergroup.role.dummy;

import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * @author 강명관
 * @since 1.0
 */
public class RoleDummy {

    private RoleDummy() {

    }

    public static Role getRole() {
        return new Role(RoleEnum.USER);
    }
}
