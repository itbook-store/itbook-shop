package shop.itbook.itbookshop.membergroup.membership.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Getter;

/**
 * 회원 등급 수정을 위한 Request DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public class MembershipModifyRequestDto {
    @NotBlank(message = "등급 이름은 공백일 수 없습니다.")
    @Size(max = 20, message = "등급 이름은 20자를 넘을 수 없습니다.")
    private String membershipGrade;

    @PositiveOrZero(message = "등급 기준금애근 0보다 작을 수 없습니다.")
    private Long membershipStandardAmount;

    @PositiveOrZero(message = "등급 포인트는 0보다 작을 수 없습니다.")
    private Long membershipPoint;
}
