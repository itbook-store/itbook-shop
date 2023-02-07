package shop.itbook.itbookshop.bookmark.resultmessage;

import lombok.Getter;

/**
 * Bookmark 성공 응답에 대한 메세지를 정의하는 Enum 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public enum BookmarkResultMessageEnum {

    BOOKMARK_ADD_PRODUCT("즐겨찾기 상품 등록에 성공 했습니다.");

    private final String successMessage;

    BookmarkResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
