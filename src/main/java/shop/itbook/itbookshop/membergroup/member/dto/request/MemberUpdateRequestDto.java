package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 서비스 API에서 멤버 수정시 정보를 보관할 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
public class MemberUpdateRequestDto {

    @NotBlank(message = "닉네임은 null값 및 공백을 허용하지 않습니다.")
    @Length(min = 2, max = 20, message = "닉네임은 최소 2자, 최대 20자까지 허용합니다.")
    private String nickname;

    @NotBlank(message = "이름은 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 20, message = "이름은 최대 20자까지 허용합니다.")
    private String name;

    @NotBlank(message = "비밀번호는 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 255, message = "비밀번호는 최대 255자까지 허용합니다.")
    private String password;

    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

}
