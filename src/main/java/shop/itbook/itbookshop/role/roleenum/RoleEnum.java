package shop.itbook.itbookshop.role.roleenum;

import lombok.Getter;

/**
 * 회원 권한의 타입안정성을 지켜주는 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum RoleEnum {
    ADMIN("관리자"), MANAGER("직원"), USER("회원"), AUTHOR("작가"), GUEST("비회원");

    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public static RoleEnum stringToEnum(String s) {
        for (RoleEnum value : RoleEnum.values()) {
            if (value.getRoleName().equals(s)) {
                return value;
            }
        }
        return null;
    }
}
