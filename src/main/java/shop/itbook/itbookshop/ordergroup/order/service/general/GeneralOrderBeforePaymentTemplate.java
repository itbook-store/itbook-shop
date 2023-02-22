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
        this.saveOrderAndSub(infoForPrePaymentProcess.getOrderAddRequestDto());
        this.saveOrderPerson();
        this.saveOrderProduct();
        this.calculateTotalAmount();

        return null;
    }

    public void saveOrderAndSub(OrderAddRequestDto orderAddRequestDto) {
    }

    public void saveOrderProduct() {

    }

    @Override
    public abstract void saveOrderPerson();

    @Override
    public abstract void calculateTotalAmount();
}
