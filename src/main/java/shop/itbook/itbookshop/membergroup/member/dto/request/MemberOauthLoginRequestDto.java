package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 소셜 로그인할 때 아이디가 테이블에 존재하는지 찾기위한 DTO 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberOauthLoginRequestDto {

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    String email;

    @NotBlank(message = "인코딩된 이메일은 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 255, message = "인코딩된 이메일은 최대 255자까지 허용합니다.")
    String encodedEmail;

}
