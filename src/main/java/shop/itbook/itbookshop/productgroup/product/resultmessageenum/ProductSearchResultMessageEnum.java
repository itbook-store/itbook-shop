package shop.itbook.itbookshop.productgroup.product.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 상품 검색 요청 성공 시 반환해줄 성공 메세지를 열거한 enum 클래스입니다.
 *
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
