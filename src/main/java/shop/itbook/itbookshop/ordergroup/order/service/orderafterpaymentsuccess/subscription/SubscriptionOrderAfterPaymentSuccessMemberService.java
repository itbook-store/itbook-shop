package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.subscription;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply.DefaultMemberApplyWhenOrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply.MemberApplicableWhenOrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderAfterPaymentSuccessMemberService
    extends AbstractSubscriptionOrderAfterPaymentSuccess {
    private final MemberApplicableWhenOrderAfterPaymentSuccess memberApplicableWhenOrder;

    SubscriptionOrderAfterPaymentSuccessMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        DefaultMemberApplyWhenOrderAfterPaymentSuccess memberApplicableWhenOrder) {
        super(orderStatusHistoryService);

        this.memberApplicableWhenOrder = memberApplicableWhenOrder;
    }

    @Override
    public Order processOrderAfterPaymentSuccess(Order order) {
        super.changeOrderStatus(order);
        this.useCoupon(order);
        this.usePoint(order);
        return order;
    }

    private void useCoupon(Order order) {

        memberApplicableWhenOrder.useCoupon(order);
    }

    private void usePoint(Order order) {

        memberApplicableWhenOrder.usePoint(order);
    }
}
