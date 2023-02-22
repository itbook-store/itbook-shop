package shop.itbook.itbookshop.ordergroup.order.service.orderafterpayment.success.subscription;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderAfterPaymentSuccessNonMemberService
    extends SubscriptionOrderAfterPaymentSuccessTemplate {
    public SubscriptionOrderAfterPaymentSuccessNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService) {
        super(orderStatusHistoryService);
    }

    @Override
    protected void startUsageProcessing(Order order) {

    }
}
