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

    @NotBlank
    @Length(min = 2, max = 15)
    private String id;

    @NotBlank
    @Length(min = 2, max = 20)
    private String nickname;

    @NotBlank
    @Length(max = 20)
    private String name;

    @NotNull
    private Boolean isMan;

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
