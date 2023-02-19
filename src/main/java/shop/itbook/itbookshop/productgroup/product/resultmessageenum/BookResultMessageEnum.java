package shop.itbook.itbookshop.productgroup.product.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 요청 성공 시 반환해줄 성공 메세지를 열거한 enum 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public enum BookResultMessageEnum {
    GET_SUCCESS("도서 조회 성공!"),
    ADD_SUCCESS("도서 등록 성공!"),
    MODIFY_SUCCESS("도서 수정 성공!"),
    DELETE_SUCCESS("도서 삭제 성공!");

    private String message;

    BookResultMessageEnum(String message) {
        this.message = message;
    }
}
