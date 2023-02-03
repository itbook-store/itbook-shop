package shop.itbook.itbookshop.role.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {

    private Integer roleNo;
    private String roleName;
}
