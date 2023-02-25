package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.nonmember;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractNonMemberOrderBeforePaymentCancel
    implements OrderBeforePaymentCancel {

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(Order order) {

        this.processOrderBeforePaymentCancelWithoutMemberProcessing(order);
    }

    protected abstract void processOrderBeforePaymentCancelWithoutMemberProcessing(Order order);
}
