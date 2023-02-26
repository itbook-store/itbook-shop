package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.OrderAfterPaymentSuccessServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.nonmember.NonMemberOrderAfterPaymentSuccessServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.subscription.SubscriptionOrderAfterPaymentSuccessServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class NonMemberSubscriptionOrderAfterPaymentSuccessProcessor
    extends OrderAfterPaymentSuccessProcessor {

    @Qualifier("nonMemberOrderAfterPaymentSuccessServiceImpl")
    private final OrderAfterPaymentSuccessServiceAboutMemberType
        nonMemberOrderAfterPaymentSuccessServiceImpl;

    @Qualifier("subscriptionOrderAfterPaymentSuccessServiceImpl")
    private final OrderAfterPaymentSuccessServiceAboutOrderType
        subscriptionOrderAfterPaymentSuccessServiceImpl;

    @Override
    protected OrderAfterPaymentSuccessServiceAboutMemberType createOrderAfterPaymentSuccessAboutMemberTypeService() {
        return nonMemberOrderAfterPaymentSuccessServiceImpl;
    }

    @Override
    protected OrderAfterPaymentSuccessServiceAboutOrderType createOrderAfterPaymentSuccessAboutOrderTypeService() {
        return subscriptionOrderAfterPaymentSuccessServiceImpl;
    }
}
