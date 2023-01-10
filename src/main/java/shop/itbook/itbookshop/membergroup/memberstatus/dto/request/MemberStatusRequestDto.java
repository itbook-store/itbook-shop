package shop.itbook.itbookshop.membergroup.memberstatus.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 멤버 상태 생성 및 수정시 정보를 가져올 요청 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberStatusRequestDto {

    @NotBlank(message = "멤버 상태는 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 255, message = "멤버 상태는 최대 255자까지 허용합니다.")
    private String memberStatusName;
}
