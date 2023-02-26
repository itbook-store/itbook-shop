package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class OrderBeforePaymentProcessor {

    public OrderPaymentDto processOrderBeforePayment(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {

        OrderBeforePaymentServiceAboutOrderType
            orderBeforePaymentServiceAboutOrderType =
            this.createOrderBeforePaymentServiceAboutOrderType();

        orderBeforePaymentServiceAboutOrderType.processAboutOrderType(
            infoForProcessOrderBeforePayment);

        OrderBeforePaymentServiceAboutMemberType orderBeforePaymentServiceAboutMemberType =
            this.createOrderBeforePaymentServiceAboutMemberType();

        OrderPaymentDto orderPaymentDto =
            orderBeforePaymentServiceAboutMemberType.processAboutMemberType(
                infoForProcessOrderBeforePayment);

        return orderPaymentDto;
    }

    protected abstract OrderBeforePaymentServiceAboutOrderType createOrderBeforePaymentServiceAboutOrderType();

    protected abstract OrderBeforePaymentServiceAboutMemberType createOrderBeforePaymentServiceAboutMemberType();
}
