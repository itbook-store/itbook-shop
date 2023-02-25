package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.member;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply.MemberApplicableWhenOrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class MemberSubscriptionOrderAfterPaymentSuccess
    extends AbstractMemberOrderAfterPaymentSuccess {

    private final OrderStatusHistoryService orderStatusHistoryService;

    public MemberSubscriptionOrderAfterPaymentSuccess(
        MemberApplicableWhenOrderAfterPaymentSuccess memberApplicableWhenOrderAfterPaymentSuccess,
        OrderStatusHistoryService orderStatusHistoryService) {
        super(memberApplicableWhenOrderAfterPaymentSuccess);
        this.orderStatusHistoryService = orderStatusHistoryService;
    }

    @Override
    protected void processOrderAfterPaymentSuccessWithoutMemberProcessing(Order order) {
        this.changeOrderStatus(order);
    }

    protected void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);
    }
}
