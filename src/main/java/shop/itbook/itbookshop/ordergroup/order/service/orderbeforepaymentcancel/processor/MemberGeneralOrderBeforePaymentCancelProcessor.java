package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.OrderBeforePaymentCancelServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class MemberGeneralOrderBeforePaymentCancelProcessor
    extends OrderBeforePaymentCancelProcessor {

    @Qualifier("memberOrderBeforePaymentCancelServiceImpl")
    private final OrderBeforePaymentCancelServiceAboutMemberType
        memberOrderBeforePaymentCancelServiceImpl;

    @Qualifier("generalOrderBeforePaymentCancelServiceImpl")
    private final OrderBeforePaymentCancelServiceAboutOrderType
        generalOrderBeforePaymentCancelServiceImpl;


    @Override
    protected OrderBeforePaymentCancelServiceAboutMemberType createOrderBeforePaymentCancelServiceAboutMemberType() {
        return memberOrderBeforePaymentCancelServiceImpl;
    }

    @Override
    protected OrderBeforePaymentCancelServiceAboutOrderType createOrderBeforePaymentCancelServiceAboutOrderType() {
        return generalOrderBeforePaymentCancelServiceImpl;
    }
}
