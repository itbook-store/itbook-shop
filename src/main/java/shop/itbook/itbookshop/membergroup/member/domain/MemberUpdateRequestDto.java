package shop.itbook.itbookshop.membergroup.member.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    private String nickname;
    private String name;
    private LocalDateTime birth;
    private String password;
    private String phoneNumber;
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
