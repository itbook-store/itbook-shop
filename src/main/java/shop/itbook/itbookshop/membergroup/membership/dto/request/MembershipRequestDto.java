package shop.itbook.itbookshop.membergroup.membership.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Getter;

/**
 * 회원 등급에 요청 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public class MembershipRequestDTO {

    @NotBlank
    @Size(max = 20)
    private String membershipGrade;

    @PositiveOrZero
    private Long membershipStandardAmount;

    @PositiveOrZero
    private Long membershipPoint;
}
