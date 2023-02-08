package shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 연관상품에 관한 정보를 반환하기 위한 ResponseDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRelationResponseDto {
    private Long productNo;
    private String productName;
    private Long productRelationCount;
}
