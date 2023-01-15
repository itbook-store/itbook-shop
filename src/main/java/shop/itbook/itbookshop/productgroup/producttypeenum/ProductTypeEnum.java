package shop.itbook.itbookshop.productgroup.producttypeenum;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import shop.itbook.itbookshop.productgroup.producttype.converter.impl.ProductTypeEnumConverter;

/**
 * 상품유형에 대한 enum입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public enum ProductTypeEnum {
    BESTSELLER("베스트셀러"), RECOMMENDATION("추천"), RECENTLY_SEEN_PRODUCT("최근본상품"),
    NEW_ISSUE("신간"), POPULARITY("인기"), DISCOUNT("할인");

    private final String productType;

    ProductTypeEnum(String productType) {
        this.productType = productType;
    }

    public static ProductTypeEnum stringToEnum(String s) {

        for (ProductTypeEnum value : ProductTypeEnum.values()) {
            if (value.getProductType().equals(s)) {
                return value;
            }
        }
        return null;
    }
}
