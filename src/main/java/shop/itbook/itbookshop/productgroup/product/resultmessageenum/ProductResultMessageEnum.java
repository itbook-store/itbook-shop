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
    GET_SUCCESS("상품 조회 성공!"),
    ADD_SUCCESS("상품 등록 성공!"),
    MODIFY_SUCCESS("상품 수정 성공!"),
    DELETE_SUCCESS("상품 삭제 성공!");

    private String message;

    ProductResultMessageEnum(String message) {
        this.message = message;
    }
}
