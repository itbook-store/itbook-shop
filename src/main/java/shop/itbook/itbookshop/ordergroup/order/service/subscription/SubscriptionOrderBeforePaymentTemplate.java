package shop.itbook.itbookshop.ordergroup.order.service.subscription;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.service.OrderBeforePayment;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class SubscriptionOrderBeforePaymentTemplate implements OrderBeforePayment {

    @Override
    public OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        this.saveOrderAndSub(infoForPrePaymentProcess.getOrderAddRequestDto(),
            infoForPrePaymentProcess.getSequence());
        this.saveOrderPerson(infoForPrePaymentProcess);
        this.saveOrderProduct();
        this.calculateTotalAmount(infoForPrePaymentProcess);
        this.saveOrderAndSub(infoForPrePaymentProcess.getOrderAddRequestDto(),
            infoForPrePaymentProcess.getSequence());

        return null;
    }

    ;

    public void saveOrderAndSub(OrderAddRequestDto orderAddRequestDto, Long sequence) {

    }

    @Override
    public void saveOrderProduct() {

    }

    @Override
    public abstract void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    @Override
    public abstract OrderPaymentDto calculateTotalAmount(
        InfoForPrePaymentProcess infoForPrePaymentProcess);
}
