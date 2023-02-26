package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor.OrderBeforePaymentProcessor;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.OrderBeforePaymentCancelServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.member.MemberOrderBeforePaymentCancelServiceImpl;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.general.GeneralOrderBeforePaymentCancelServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class MemberGeneralOrderBeforePaymentCancelProcessor
    extends OrderBeforePaymentCancelProcessor {

    private final MemberOrderBeforePaymentCancelServiceImpl
        memberOrderBeforePaymentCancelServiceImpl;

    private final GeneralOrderBeforePaymentCancelServiceImpl
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
