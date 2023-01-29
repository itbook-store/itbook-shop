package shop.itbook.itbookshop.ordergroup.ordersheet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문서 작성에 필요한 상품 정보를 담은 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSheetProductResponseDto {

    private String productName;
    private Long fixedPrice;
    private Double discountPercent;
}
