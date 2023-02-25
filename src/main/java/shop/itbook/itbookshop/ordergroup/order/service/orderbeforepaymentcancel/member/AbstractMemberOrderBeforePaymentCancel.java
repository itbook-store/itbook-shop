package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.member;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.memberapply.MemberApplicableWhenOrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractMemberOrderBeforePaymentCancel implements OrderBeforePaymentCancel {

    private final MemberApplicableWhenOrderBeforePaymentCancel
        memberApplicableWhenOrderBeforePaymentCancel;

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(Order order) {

        this.processOrderBeforePaymentCancelWithoutMemberProcessing(order);
        this.rollbackAboutOrderSuccess(order);
    }

    protected abstract void processOrderBeforePaymentCancelWithoutMemberProcessing(Order order);

    private void rollbackAboutOrderSuccess(Order order) {
        memberApplicableWhenOrderBeforePaymentCancel.rollbackAboutOrderSuccess(order);
    }
}
