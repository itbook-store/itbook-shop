package shop.itbook.itbookshop.membergroup.member.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
public class MemberSocialRequestDto {

    @Length(min = 2, max = 20, message = "닉네임은 최소 2자, 최대 20자까지 허용합니다.")
    @NotBlank(message = "닉네임은 null값 및 공백을 허용하지 않습니다.")
    private String nickname;

    @Length(max = 20, message = "이름은 최대 20자까지 허용합니다.")
    @NotBlank(message = "이름은 null값 및 공백을 허용하지 않습니다.")
    private String name;

    @NotNull(message = "생일은 null값을 허용하지 않습니다.")
    private LocalDateTime birth;

    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

    @NotNull(message = "소셜여부는 null값을 허용하지 않습니다.")
    private Boolean isSocial;

}
