package shop.itbook.itbookshop.productgroup.producttype.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 이하늬
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductTypeResponseDto {
    private Integer productTypeNo;
    private String productTypeName;
}
