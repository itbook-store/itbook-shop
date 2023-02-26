package shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;

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
