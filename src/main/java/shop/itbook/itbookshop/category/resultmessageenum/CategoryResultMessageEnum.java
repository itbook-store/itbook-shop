package shop.itbook.itbookshop.category.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 요청 행위 성공시 반환해줄 성공메세지를 열거한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum CategoryResultMessageEnum {

    CATEGORY_SAVE_SUCCESS_MESSAGE("카테고리 삽입에 성공했습니다."),
    CATEGORY_LIST_SUCCESS_MESSAGE("모든 카테고리 반환에 성공했습니다."),
    CATEGORY_CHILD_LIST_SUCCESS_MESSAGE("특정 카테고리에 대한 모든 자식 카테고리 반환에 성공했습니다. 자식카테고리가 없을수도 있습니다."),
    CATEGORY_DETAILS_SUCCESS_MESSAGE("특정 카테고리 세부정보 반환해 성공했습니다.");

    private String successMessage;

    CategoryResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
