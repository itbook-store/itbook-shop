package shop.itbook.itbookshop.cart.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 상품 수량 수정에 대한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartModifyRequestDto {

    @NotNull(message = "회원번호는 필수입니다.")
    private Long memberNo;

    @NotNull(message = "상품번호는 필수입니다.")
    private Long productNo;

    @NotNull(message = "상품번호는 필수입니다.")
    @Positive(message = "상품의 숫자는 0보다 작을 수 없습니다.")
    private Integer productCount;

}
