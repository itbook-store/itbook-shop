package shop.itbook.itbookshop.deliverygroup.delivery.exception;

import shop.itbook.itbookshop.deliverygroup.delivery.resultmessageenum.DeliveryResultMessageEnum;

/**
 * 배송 서버에 보낼 배송 대기 상태의 배송 데이터가 없을 경우 발생하는 예외.
 *
 * @author 정재원
 * @since 1.0
 */
public class DeliveryNoWaitStatusException extends RuntimeException {
    public DeliveryNoWaitStatusException() {
        super(DeliveryResultMessageEnum.DELIVERY_NO_WAIT_LIST_MESSAGE.getResultMessage());
    }
}
