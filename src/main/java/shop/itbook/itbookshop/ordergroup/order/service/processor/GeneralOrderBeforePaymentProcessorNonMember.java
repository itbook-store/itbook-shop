package shop.itbook.itbookshop.ordergroup.order.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class GeneralOrderBeforePaymentProcessorNonMember extends OrderBeforePaymentProcessor {

    private final OrderBeforePayment generalOrderBeforePaymentNonMemberService;

    @Override
    protected OrderBeforePayment getOrderBeforePayment() {
        return generalOrderBeforePaymentNonMemberService;
    }
}
