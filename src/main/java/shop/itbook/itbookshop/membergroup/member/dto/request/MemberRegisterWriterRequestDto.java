package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 작가 계정으로 등록할 계정 정보가 담긴 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterWriterRequestDto {

    @NotNull(message = "멤버번호는 null을 허용하지 않습니다.")
    private Long memberNo;

    @Length(min = 2, max = 15, message = "아이디는 최소 2자부터 시작하며 최대 15자까지 작성해야합니다.")
    @NotBlank(message = "아이디는 null값 및 공백을 허용하지 않습니다.")
    private String memberId;
}
