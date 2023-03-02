package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.OrderBeforePaymentCancelServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.member.MemberOrderBeforePaymentCancelServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.subscription.SubscriptionOrderBeforePaymentCancelServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberSubscriptionOrderBeforePaymentCancelProcessor
    extends OrderBeforePaymentCancelProcessor {

    @Qualifier("memberOrderBeforePaymentCancelServiceImpl")
    private final OrderBeforePaymentCancelServiceAboutMemberType
        memberOrderBeforePaymentCancelServiceImpl;
    @Qualifier("subscriptionOrderBeforePaymentCancelServiceImpl")
    private final OrderBeforePaymentCancelServiceAboutOrderType
        subscriptionOrderBeforePaymentCancelServiceImpl;

    @Override
    protected OrderBeforePaymentCancelServiceAboutMemberType createOrderBeforePaymentCancelServiceAboutMemberType() {
        return memberOrderBeforePaymentCancelServiceImpl;
    }

    @Override
    protected OrderBeforePaymentCancelServiceAboutOrderType createOrderBeforePaymentCancelServiceAboutOrderType() {
        return subscriptionOrderBeforePaymentCancelServiceImpl;
    }
}
