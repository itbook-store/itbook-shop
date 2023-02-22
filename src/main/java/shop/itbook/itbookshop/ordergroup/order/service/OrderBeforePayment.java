package shop.itbook.itbookshop.ordergroup.order.service;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePayment {
    OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess);

    void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    void saveOrderProduct();

    OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess);

    void saveOrderAndSub(InfoForPrePaymentProcess infoForPrePaymentProcess);
}
