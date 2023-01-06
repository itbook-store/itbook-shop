package shop.itbook.itbookshop.productgroup.producttypeenum;

import lombok.Getter;

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
}
