package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.general;

import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class GeneralOrderBeforePaymentTemplate implements OrderBeforePayment {

    @Override
    public OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        this.saveOrder(infoForPrePaymentProcess);
        this.saveOrderPerson(infoForPrePaymentProcess);
        this.saveOrderProduct();

        return this.calculateTotalAmount(infoForPrePaymentProcess);
    }

    @Override
    public void saveOrder(InfoForPrePaymentProcess infoForPrePaymentProcess) {
    }

    public void saveOrderProduct() {

    }

    @Override
    public abstract void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    @Override
    public abstract OrderPaymentDto calculateTotalAmount(
        InfoForPrePaymentProcess infoForPrePaymentProcess);
}
