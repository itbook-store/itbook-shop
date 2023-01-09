package shop.itbook.itbookshop.productgroup.product.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 요청 성공 시 반환해줄 성공 메세지를 열거한 enum 클래스입니다.
 *
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
