package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.OrderAfterPaymentSuccessServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.member.MemberOrderAfterPaymentSuccessServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.general.GeneralOrderAfterPaymentSuccessServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberGeneralOrderAfterPaymentSuccessProcessor
    extends OrderAfterPaymentSuccessProcessor {

    private final MemberOrderAfterPaymentSuccessServiceImpl
        memberOrderAfterPaymentSuccessServiceImpl;
    private final GeneralOrderAfterPaymentSuccessServiceImpl
        generalOrderAfterPaymentSuccessServiceImpl;

    @Override
    protected OrderAfterPaymentSuccessServiceAboutMemberType createOrderAfterPaymentSuccessAboutMemberTypeService() {
        return memberOrderAfterPaymentSuccessServiceImpl;
    }

    @Override
    protected OrderAfterPaymentSuccessServiceAboutOrderType createOrderAfterPaymentSuccessAboutOrderTypeService() {
        return generalOrderAfterPaymentSuccessServiceImpl;
    }
}
