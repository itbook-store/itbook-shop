package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.OrderBeforePaymentServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;

/**
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class NonMemberGeneralOrderBeforePaymentProcessor extends OrderBeforePaymentProcessor {

    @Qualifier("nonMemberOrderBeforePaymentServiceImpl")
    private final OrderBeforePaymentServiceAboutMemberType nonMemberOrderBeforePaymentServiceImpl;
    @Qualifier("generalOrderBeforePaymentServiceImpl")
    private final OrderBeforePaymentServiceAboutOrderType generalOrderBeforePaymentServiceImpl;

    @Override
    protected OrderBeforePaymentServiceAboutOrderType createOrderBeforePaymentServiceAboutOrderType() {
        return generalOrderBeforePaymentServiceImpl;
    }

    @Override
    protected OrderBeforePaymentServiceAboutMemberType createOrderBeforePaymentServiceAboutMemberType() {
        return nonMemberOrderBeforePaymentServiceImpl;
    }
}
