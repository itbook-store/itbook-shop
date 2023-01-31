package shop.itbook.itbookshop.ordergroup.ordersheet.resultmessageenum;

import lombok.Getter;

/**
 * 주문서 로직의 결과에 따른 응답 메세지를 담고 있는 Enum
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public enum OrderSheetMessageEnum {

    ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE("주문서 작성에 필요한 상품정보화 회원 정보를 조회하는데 성공했습니다.");

    private String successMessage;

    OrderSheetMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
