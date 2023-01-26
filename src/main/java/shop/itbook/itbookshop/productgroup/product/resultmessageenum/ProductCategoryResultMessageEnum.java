package shop.itbook.itbookshop.productgroup.product.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 요청 성공 시 반환해줄 성공 메세지를 열거한 enum 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public enum ProductCategoryResultMessageEnum {
    GET_SUCCESS("상품카테고리 조회 성공!");

    private String message;

    ProductCategoryResultMessageEnum(String message) {
        this.message = message;
    }
}
