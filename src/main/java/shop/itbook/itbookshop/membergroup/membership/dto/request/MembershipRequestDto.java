package shop.itbook.itbookshop.membergroup.membership.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 등급에 요청 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipRequestDto {

    @NotBlank(message = "등급 이름은 공백일 수 없습니다.")
    @Size(max = 20, message = "등급 이름은 20자를 넘을 수 없습니다.")
    private String membershipGrade;

    @PositiveOrZero(message = "등급 기준금액은 0보다 작을 수 없습니다.")
    private Long membershipStandardAmount;

    @PositiveOrZero(message = "등급 포인트는 0보다 작을 수 없습니다.")
    private Long membershipPoint;
}
