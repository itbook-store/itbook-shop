package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePayment {

    OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess);

    void saveOrder(InfoForPrePaymentProcess infoForPrePaymentProcess);

    void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess);
}
