package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class MemberSocialRequestDto {

    @NotNull(message = "멤버 번호는 null일 수 없습니다.")
    private Long memberNo;

    @Length(min = 2, max = 15, message = "아이디는 최소 2자부터 시작하며 최대 15자까지 작성해야합니다.")
    @NotBlank(message = "아이디는 null값 및 공백을 허용하지 않습니다.")
    private String memberId;

    @Length(min = 2, max = 20, message = "닉네임은 최소 2자, 최대 20자까지 허용합니다.")
    @NotBlank(message = "닉네임은 null값 및 공백을 허용하지 않습니다.")
    private String nickname;

    @Length(max = 20, message = "이름은 최대 20자까지 허용합니다.")
    @NotBlank(message = "이름은 null값 및 공백을 허용하지 않습니다.")
    private String name;

    @NotNull(message = "성별은 null값을 허용하지 않습니다.")
    private Boolean isMan;

    @NotNull(message = "생일은 null값을 허용하지 않습니다.")
    private String birth;

    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

    @NotNull(message = "소셜여부는 null값을 허용하지 않습니다.")
    private Boolean isSocial;

    @NotNull(message = "작가여부는 null값을 허용하지 않습니다.")
    private Boolean isWriter;
}
