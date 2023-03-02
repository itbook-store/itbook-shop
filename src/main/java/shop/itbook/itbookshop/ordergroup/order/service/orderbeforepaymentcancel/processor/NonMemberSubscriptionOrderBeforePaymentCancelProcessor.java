package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.OrderBeforePaymentCancelServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;

/**
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class NonMemberSubscriptionOrderBeforePaymentCancelProcessor
    extends OrderBeforePaymentCancelProcessor {

    @Qualifier("nonMemberOrderBeforePaymentCancelServiceImpl")
    private final OrderBeforePaymentCancelServiceAboutMemberType
        nonMemberOrderBeforePaymentCancelServiceServiceImpl;
    @Qualifier("subscriptionOrderBeforePaymentCancelServiceImpl")
    private final OrderBeforePaymentCancelServiceAboutOrderType
        subscriptionOrderBeforePaymentCancelServiceImpl;

    @Override
    protected OrderBeforePaymentCancelServiceAboutMemberType createOrderBeforePaymentCancelServiceAboutMemberType() {
        return nonMemberOrderBeforePaymentCancelServiceServiceImpl;
    }

    @Override
    protected OrderBeforePaymentCancelServiceAboutOrderType createOrderBeforePaymentCancelServiceAboutOrderType() {
        return subscriptionOrderBeforePaymentCancelServiceImpl;
    }
}
