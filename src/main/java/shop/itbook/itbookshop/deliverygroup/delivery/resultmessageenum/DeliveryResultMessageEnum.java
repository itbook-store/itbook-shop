package shop.itbook.itbookshop.deliverygroup.delivery.resultmessageenum;

import lombok.Getter;

/**
 * 컨트롤러에서 로직이 끝나고 반환할 메세지를 열거한 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public enum DeliveryResultMessageEnum {

    DELIVERY_SAVE_SUCCESS_MESSAGE("배송 삽입에 성공했습니다."),
    DELIVERY_LIST_SUCCESS_MESSAGE("배송 상태를 포함한 배송 정보 리스트 반환에 성공했습니다."),
    DELIVERY_NO_WAIT_LIST_MESSAGE("배송 대기 중인 배송 정보가 없습니다.");

    private final String resultMessage;

    DeliveryResultMessageEnum(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
