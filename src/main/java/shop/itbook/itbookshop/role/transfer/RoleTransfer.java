package shop.itbook.itbookshop.role.transfer;

import shop.itbook.itbookshop.role.dto.RoleResponseDto;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * @author 노수연
 * @since 1.0
 */
public class RoleTransfer {

    public static Role dtoToEntity(RoleResponseDto roleResponseDto) {
        return new Role(roleResponseDto.getRoleNo(), RoleEnum.stringToEnum(
            roleResponseDto.getRoleName()));
    }
}
