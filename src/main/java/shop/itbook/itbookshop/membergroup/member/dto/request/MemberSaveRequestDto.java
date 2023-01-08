package shop.itbook.itbookshop.membergroup.member.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String id;
    private String nickname;
    private String name;
    private boolean isMan;
    private LocalDateTime birth;
    private String password;
    private String phoneNumber;
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
