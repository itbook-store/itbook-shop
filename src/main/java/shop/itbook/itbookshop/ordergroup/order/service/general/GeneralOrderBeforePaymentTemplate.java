package shop.itbook.itbookshop.ordergroup.order.service.general;

import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.service.OrderBeforePayment;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class GeneralOrderBeforePaymentTemplate implements OrderBeforePayment {

    @Override
    public OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        this.saveOrderAndSub(infoForPrePaymentProcess);
        this.saveOrderPerson(infoForPrePaymentProcess);
        this.saveOrderProduct();

        return this.calculateTotalAmount(infoForPrePaymentProcess);
    }

    @Override
    public void saveOrderAndSub(InfoForPrePaymentProcess infoForPrePaymentProcess) {
    }

    public void saveOrderProduct() {

    }

    @Override
    public abstract void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    @Override
    public abstract OrderPaymentDto calculateTotalAmount(
        InfoForPrePaymentProcess infoForPrePaymentProcess);
}
