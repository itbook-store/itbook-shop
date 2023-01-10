package shop.itbook.itbookshop.membergroup.member.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 멤버 수정시 정보를 보관할 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String nickname;

    @NotBlank
    @Length(max = 20)
    private String name;

    @NotNull
    private LocalDateTime birth;

    @NotBlank
    @Length(max = 255)
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @Builder
    public MemberUpdateRequestDto(String nickname, String name, LocalDateTime birth,
                                  String password,
                                  String phoneNumber, String email) {
        this.nickname = nickname;
        this.name = name;
        this.birth = birth;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
