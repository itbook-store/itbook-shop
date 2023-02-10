package shop.itbook.itbookshop.membergroup.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointSendRequestDto {

    @NotNull(message = "포인트 받는 회원 번호는 null 일 수 없습니다.")
    private Long receiveMemberNo;

    @PositiveOrZero(message = "포인트는 양수여야 합니다.")
    @NotBlank(message = "포인트는 null이거나 공백일 수 없습니다.")
    private Long giftPoint;

}
