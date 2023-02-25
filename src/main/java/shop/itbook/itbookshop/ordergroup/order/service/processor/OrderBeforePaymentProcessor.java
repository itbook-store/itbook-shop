package shop.itbook.itbookshop.ordergroup.order.service.processor;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class OrderBeforePaymentProcessor {

    private OrderBeforePayment orderBeforePayment;

    public OrderPaymentDto processOrderBeforePayment(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {

        orderBeforePayment = this.getOrderBeforePayment();
        return orderBeforePayment.processOrderBeforePayment(infoForProcessOrderBeforePayment);
    }

    protected abstract OrderBeforePayment getOrderBeforePayment();
}
