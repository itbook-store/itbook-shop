package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,255}",
        message = "비밀번호는 영문 대소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함되어야하고 길이는 8자 ~ 255자의 비밀번호여야 합니다.")
    private String password;

    @Pattern(regexp = "^[0-9]{11}$", message = "전화번호 형식에 맞춰 입력해주세요. 숫자만 입력할 수 있습니다.")
    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

}
