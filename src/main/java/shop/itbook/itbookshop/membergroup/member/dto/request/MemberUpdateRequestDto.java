package shop.itbook.itbookshop.membergroup.member.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 멤버 수정시 정보를 보관할 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
public class MemberUpdateRequestDto {

    private String nickname;

    private String name;

    private String password;

    private String phoneNumber;

    private String email;

}
