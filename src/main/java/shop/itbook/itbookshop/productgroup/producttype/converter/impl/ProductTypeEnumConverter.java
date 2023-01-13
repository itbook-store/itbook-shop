package shop.itbook.itbookshop.productgroup.producttype.converter.impl;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
@Converter
public class ProductTypeEnumConverter implements AttributeConverter<ProductTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(ProductTypeEnum productTypeEnum) {
        return productTypeEnum.getProductType();
    }

    @Override
    public ProductTypeEnum convertToEntityAttribute(String dbData) {
        ProductTypeEnum productTypeEnum = ProductTypeEnum.stringToEnum(dbData);
        return productTypeEnum;
    }
}
