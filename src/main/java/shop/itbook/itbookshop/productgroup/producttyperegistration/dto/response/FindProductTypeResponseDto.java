package shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindProductTypeResponseDto {
    private ProductTypeEnum productTypeName;
}
