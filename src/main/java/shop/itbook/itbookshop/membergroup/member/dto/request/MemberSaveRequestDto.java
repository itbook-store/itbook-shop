package shop.itbook.itbookshop.membergroup.member.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 멤버 생성시 정보를 보관할 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    @NotBlank(message = "아이디는 null값 및 공백을 허용하지 않습니다.")
    @Length(min = 2, max = 15, message = "아이디는 최소 2자부터 시작하며 최대 15자까지 작성해야합니다.")
    private String id;

    @NotBlank(message = "닉네임은 null값 및 공백을 허용하지 않습니다.")
    @Length(min = 2, max = 20, message = "닉네임은 최소 2자, 최대 20자까지 허용합니다.")
    private String nickname;

    @NotBlank(message = "이름은 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 20, message = "이름은 최대 20자까지 허용합니다.")
    private String name;

    @NotNull(message = "성별은 null값을 허용하지 않습니다.")
    private Boolean isMan;

    @NotNull(message = "생일은 null값을 허용하지 않습니다.")
    private LocalDateTime birth;

    @NotBlank(message = "비밀번호는 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 255, message = "비밀번호는 최대 255자까지 허용합니다.")
    private String password;

    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

    @Builder
    public MemberSaveRequestDto(String id, String nickname, String name, boolean isMan,
                                LocalDateTime birth, String password, String phoneNumber,
                                String email) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.isMan = isMan;
        this.birth = birth;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
