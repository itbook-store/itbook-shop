package shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindProductTypeNameResponseDto {
    private ProductTypeResponseDto productTypeResponseDto;
}
