package shop.itbook.itbookshop.ordergroup.order.resultemessageenum;

import lombok.Getter;

/**
 * 주문 컨트롤러에서 로직이 끝나고 반환 시 보여줄 메세지를 나열한 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public enum OrderResultMessageEnum {

    ORDER_ADD_SUCCESS_MESSAGE("주문 추가에 성공하였습니다"),
    ORDER_LIST_OF_MEMBER_FIND_SUCCESS_MESSAGE("회원의 주문 목록 조회에 성공했습니다."),
    ORDER_LIST_OF_NON_MEMBER_FIND_SUCCESS_MESSAGE("비회원의 주문 조회에 성공했습니다."),
    ORDER_SHEET_SUCCESS_MESSAGE("회원의 주문서 작성이 완료되었습니다."),
    ORDER_PAY_SUCCESS_MESSAGE("회원이 주문한 건의 결제가 완료되었습니다."),
    ORDER_DETAILS_FIND_SUCCESS_MESSAGE("주문 상세 조회에 성공했습니다."),
    ORDER_LIST_OF_ADMIN_FIND_SUCCESS_MESSAGE("관리자의 주문 목록 조회에 성공했습니다.");

    private final String resultMessage;

    OrderResultMessageEnum(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
