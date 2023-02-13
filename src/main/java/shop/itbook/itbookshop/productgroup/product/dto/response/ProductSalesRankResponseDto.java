package shop.itbook.itbookshop.productgroup.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 이하늬
 * @since 1.0
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProductSalesRankResponseDto {
    Long productNo;
    String productName;
    Integer count;
}
