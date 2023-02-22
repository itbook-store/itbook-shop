package shop.itbook.itbookshop.ordergroup.order.service.subscription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderNonMemberService
    extends SubscriptionOrderBeforePaymentTemplate {

    private final OrderRepository orderRepository;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;

    public SubscriptionOrderNonMemberService(
        OrderRepository orderRepository, OrderSubscriptionRepository orderSubscriptionRepository,
        OrderStatusHistoryService orderStatusHistoryService) {
        super(orderRepository, orderSubscriptionRepository, orderStatusHistoryService);
        this.orderRepository = orderRepository;
        this.orderSubscriptionRepository = orderSubscriptionRepository;
        this.orderStatusHistoryService = orderStatusHistoryService;
    }

    @Override
    public void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess) {

    }

    @Override
    public OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        return null;
    }
}
