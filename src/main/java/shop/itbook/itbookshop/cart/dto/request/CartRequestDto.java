package shop.itbook.itbookshop.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 요청애 등록과, 삭제에 대한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    private Long memberNo;
    private Long productNo;

}
