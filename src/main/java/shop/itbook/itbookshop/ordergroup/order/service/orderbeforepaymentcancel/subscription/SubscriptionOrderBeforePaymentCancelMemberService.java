package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.subscription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.memberapply.MemberApplicableWhenOrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderBeforePaymentCancelMemberService
    extends AbstractSubscriptionOrderBeforePaymentCancel {
    private final MemberApplicableWhenOrderBeforePaymentCancel
        memberApplicableWhenOrderBeforePaymentCancel;

    public SubscriptionOrderBeforePaymentCancelMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        OrderSubscriptionRepository orderSubscriptionRepository,
        OrderRepository orderRepository,
        OrderStatusService orderStatusService,
        MemberApplicableWhenOrderBeforePaymentCancel memberApplicableWhenOrderBeforePaymentCancel) {
        super(orderStatusHistoryService, orderSubscriptionRepository, orderRepository,
            orderStatusService);

        this.memberApplicableWhenOrderBeforePaymentCancel =
            memberApplicableWhenOrderBeforePaymentCancel;
    }

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(Order order) {

        super.checkOrderStatus(order.getOrderNo());

        this.rollbackAboutOrderSuccess(order);

        super.changeOrderStatusAboutOrderCancel(order);
    }

    private void rollbackAboutOrderSuccess(Order order) {

        memberApplicableWhenOrderBeforePaymentCancel.rollbackAboutOrderSuccess(order);
    }

}
