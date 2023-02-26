package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.subscription.SubscriptionOrderBeforePaymentServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;

/**
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class MemberSubscriptionOrderBeforePaymentProcessor extends OrderBeforePaymentProcessor {

    @Qualifier("memberOrderBeforePaymentServiceImpl")
    private final OrderBeforePaymentServiceAboutMemberType memberOrderBeforePaymentServiceImpl;
    @Qualifier("subscriptionOrderBeforePaymentServiceImpl")
    private final OrderBeforePaymentServiceAboutOrderType
        subscriptionOrderBeforePaymentServiceImpl;

    @Override
    protected OrderBeforePaymentServiceAboutOrderType createOrderBeforePaymentServiceAboutOrderType() {
        return subscriptionOrderBeforePaymentServiceImpl;
    }

    @Override
    protected OrderBeforePaymentServiceAboutMemberType createOrderBeforePaymentServiceAboutMemberType() {
        return memberOrderBeforePaymentServiceImpl;
    }
}
