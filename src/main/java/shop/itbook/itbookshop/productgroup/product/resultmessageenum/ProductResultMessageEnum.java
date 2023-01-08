package shop.itbook.itbookshop.productgroup.product.resultmessageenum;

import lombok.Getter;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
public enum ProductResultMessageEnum {

    ADD_SUCCESS("product registration successful!"),
    MODIFY_SUCCESS("product modification successful!"),
    DELETE_SUCCESS("product deletion successful!");

    private String message;

    ProductResultMessageEnum(String message) {
        this.message = message;
    }
}
