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
    @NotBlank
    @Size(max = 20)
    private String membershipGrade;

    @PositiveOrZero
    private Long membershipStandardAmount;

    @PositiveOrZero
    private Long membershipPoint;
}
