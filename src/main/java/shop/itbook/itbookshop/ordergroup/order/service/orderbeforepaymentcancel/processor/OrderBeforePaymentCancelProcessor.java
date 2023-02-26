package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.OrderBeforePaymentCancelServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class OrderBeforePaymentCancelProcessor {

    public void processOrderBeforePaymentCancel(Order order) {

        OrderBeforePaymentCancelServiceAboutMemberType
            orderBeforePaymentCancelServiceAboutMemberType =
            this.createOrderBeforePaymentCancelServiceAboutMemberType();

        orderBeforePaymentCancelServiceAboutMemberType.processAboutMemberType(order);

        OrderBeforePaymentCancelServiceAboutOrderType
            orderBeforePaymentCancelServiceAboutOrderType =
            this.createOrderBeforePaymentCancelServiceAboutOrderType();

        orderBeforePaymentCancelServiceAboutOrderType.processAboutOrderType(order);
    }

    protected abstract OrderBeforePaymentCancelServiceAboutMemberType createOrderBeforePaymentCancelServiceAboutMemberType();

    protected abstract OrderBeforePaymentCancelServiceAboutOrderType createOrderBeforePaymentCancelServiceAboutOrderType();
}
