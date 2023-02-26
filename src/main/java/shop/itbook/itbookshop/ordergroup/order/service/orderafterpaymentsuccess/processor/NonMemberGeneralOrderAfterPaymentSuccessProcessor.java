package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.OrderAfterPaymentSuccessServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class NonMemberGeneralOrderAfterPaymentSuccessProcessor
    extends OrderAfterPaymentSuccessProcessor {

    @Qualifier("nonMemberOrderAfterPaymentSuccessServiceImpl")
    private final OrderAfterPaymentSuccessServiceAboutMemberType
        nonMemberOrderAfterPaymentSuccessServiceImpl;
    @Qualifier("generalOrderAfterPaymentSuccessServiceImpl")
    private final OrderAfterPaymentSuccessServiceAboutOrderType
        generalOrderAfterPaymentSuccessServiceImpl;

    @Override
    protected OrderAfterPaymentSuccessServiceAboutMemberType createOrderAfterPaymentSuccessAboutMemberTypeService() {
        return nonMemberOrderAfterPaymentSuccessServiceImpl;
    }

    @Override
    protected OrderAfterPaymentSuccessServiceAboutOrderType createOrderAfterPaymentSuccessAboutOrderTypeService() {
        return generalOrderAfterPaymentSuccessServiceImpl;
    }
}
