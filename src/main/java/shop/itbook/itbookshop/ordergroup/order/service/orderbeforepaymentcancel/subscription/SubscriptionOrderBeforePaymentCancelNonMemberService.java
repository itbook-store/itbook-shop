package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.subscription;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderBeforePaymentCancelNonMemberService
    extends SubscriptionOrderBeforePaymentCancelTemplate {
    public SubscriptionOrderBeforePaymentCancelNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        OrderSubscriptionRepository orderSubscriptionRepository,
        OrderRepository orderRepository,
        OrderStatusService orderStatusService) {
        super(orderStatusHistoryService, orderSubscriptionRepository, orderRepository,
            orderStatusService);
    }

    @Override
    protected void startUsageProcessing(Order order) {

    }
}
