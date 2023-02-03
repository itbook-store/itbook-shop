package shop.itbook.itbookshop.cart.resultmessage;

import lombok.Getter;

/**
 * 공용응답객체의 성공 메시지 Enum 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public enum CartResultMessageEnum {
    CART_REGISTER_SUCCESS_MESSAGE("장바구니 등록에 성공하였습니다."),
    CART_LIST_SUCCESS_MESSAGE("장바구니 상품 리스트 반환에 성공하였습니다."),
    CART_DELETE_SUCCESS_MESSAGE("장바구니 상품 삭제에 성공하였습니다."),
    CART_DELETE_ALL_SUCCESS_MESSAGE("장바구니 모든 상품 삭제에 성공하였습니다."),
    CART_MODIFY_PRODUCT_COUNT_SUCCESS_MESSAGE("장바구니 상품 갯수 수정에 성공하였습니다.");


    private final String successMessage;

    CartResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
