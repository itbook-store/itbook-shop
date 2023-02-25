package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.subscription;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderAfterPaymentSuccessNonMemberService
    extends AbstractSubscriptionOrderAfterPaymentSuccess {
    SubscriptionOrderAfterPaymentSuccessNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService) {
        super(orderStatusHistoryService);
    }

    @Override
    public Order processOrderAfterPaymentSuccess(Order order) {
        super.changeOrderStatus(order);
        return order;
    }
}
