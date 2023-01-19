package shop.itbook.itbookshop.membergroup.memberrole.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원의 권한을 갖고오는 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleResponseDto {
    private String role;
}
