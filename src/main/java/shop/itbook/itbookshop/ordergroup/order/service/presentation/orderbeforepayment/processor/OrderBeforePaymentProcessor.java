package shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.processor;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전_처리전반에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.dto.response.결제_요청에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class OrderBeforePaymentProcessor {

    public 결제_요청에_필요한_정보_클래스 processOrderBeforePayment(
        결제전_처리전반에_필요한_정보_클래스 infoForProcessOrderBeforePayment) {

        OrderBeforePaymentServiceAboutOrderType
            orderBeforePaymentServiceAboutOrderType =
            this.createOrderBeforePaymentServiceAboutOrderType();

        orderBeforePaymentServiceAboutOrderType.processAboutOrderType(
            infoForProcessOrderBeforePayment);

        OrderBeforePaymentServiceAboutMemberType orderBeforePaymentServiceAboutMemberType =
            this.createOrderBeforePaymentServiceAboutMemberType();

        결제_요청에_필요한_정보_클래스 orderPaymentDto =
            orderBeforePaymentServiceAboutMemberType.processAboutMemberType(
                infoForProcessOrderBeforePayment);

        return orderPaymentDto;
    }

    protected abstract OrderBeforePaymentServiceAboutOrderType createOrderBeforePaymentServiceAboutOrderType();

    protected abstract OrderBeforePaymentServiceAboutMemberType createOrderBeforePaymentServiceAboutMemberType();
}
