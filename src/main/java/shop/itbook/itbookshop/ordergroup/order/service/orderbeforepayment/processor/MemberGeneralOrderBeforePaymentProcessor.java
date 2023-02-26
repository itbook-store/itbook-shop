package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.OrderAfterPaymentSuccessServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.member.MemberOrderAfterPaymentSuccessServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.general.GeneralOrderAfterPaymentSuccessServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.member.MemberOrderBeforePaymentServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.general.GeneralOrderBeforePaymentServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class MemberGeneralOrderBeforePaymentProcessor extends OrderBeforePaymentProcessor {

    @Qualifier("memberOrderBeforePaymentServiceImpl")
    private final OrderBeforePaymentServiceAboutMemberType
        memberOrderBeforePaymentServiceImpl;

    @Qualifier("generalOrderBeforePaymentServiceImpl")
    private final OrderBeforePaymentServiceAboutOrderType generalOrderBeforePaymentServiceImpl;

    @Override
    protected OrderBeforePaymentServiceAboutOrderType createOrderBeforePaymentServiceAboutOrderType() {
        return generalOrderBeforePaymentServiceImpl;
    }

    @Override
    protected OrderBeforePaymentServiceAboutMemberType createOrderBeforePaymentServiceAboutMemberType() {
        return memberOrderBeforePaymentServiceImpl;
    }
}
