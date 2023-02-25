package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.member;

import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply.MemberApplicableWhenOrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractMemberOrderAfterPaymentSuccess implements OrderAfterPaymentSuccess {

    private final MemberApplicableWhenOrderAfterPaymentSuccess
        memberApplicableWhenOrderAfterPaymentSuccess;

    @Override
    public Order processOrderAfterPaymentSuccess(Order order) {

        this.processOrderAfterPaymentSuccessWithoutMemberProcessing(order);

        this.useCoupon(order);
        this.usePoint(order);

        return order;
    }

    protected abstract void processOrderAfterPaymentSuccessWithoutMemberProcessing(Order order);

    protected void useCoupon(Order order) {

        memberApplicableWhenOrderAfterPaymentSuccess.useCoupon(order);
    }

    protected void usePoint(Order order) {

        memberApplicableWhenOrderAfterPaymentSuccess.usePoint(order);
    }


}
