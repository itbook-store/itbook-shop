package shop.itbook.itbookshop.productgroup.product.resultmessageenum;

import lombok.Getter;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum ProductSearchResultMessageEnum {

    PRODUCT_SEARCH_SUCCESS("상품 조회 성공!");
    private String message;

    ProductSearchResultMessageEnum(String message) {
        this.message = message;
    }
}
