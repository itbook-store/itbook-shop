package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 관리자 api에서는 멤버 상태만을 변경할 수 있도록 설계한 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberStatusUpdateAdminRequestDto {

    @NotBlank(message = "멤버상태명은 null값 및 공백을 허용하지 않습니다.")
    private String memberStatusName;

    @NotNull(message = "멤버상태명은 null값 및 공백을 허용하지 않습니다.")
    @Length(max = 255, message = "상태 변경 사유는 최대 255자까지 허용합니다.")
    private String statusChangedReason;

}
