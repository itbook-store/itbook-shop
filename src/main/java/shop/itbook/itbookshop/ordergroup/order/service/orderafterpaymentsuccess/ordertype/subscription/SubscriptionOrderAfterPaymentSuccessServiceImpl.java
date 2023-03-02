package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.subscription;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudService;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class SubscriptionOrderAfterPaymentSuccessServiceImpl implements
    OrderAfterPaymentSuccessServiceAboutOrderType {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderCrudService orderCrudService;

    @Override
    public Order processAboutOrderType(Order order) {
        this.changeOrderStatus(order);
        return order;
    }

    protected void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);

        OrderSubscription orderSubscription =
            orderSubscriptionRepository.findByOrder_OrderNo(order.getOrderNo()).orElseThrow();

        Integer subscriptionPeriod = orderSubscription.getSubscriptionPeriod();

        for (long i = 1L; i < subscriptionPeriod; i++) {

            Order orderChild = orderCrudService.findOrderEntity(order.getOrderNo() + i);
            orderStatusHistoryService.addOrderStatusHistory(orderChild,
                OrderStatusEnum.PAYMENT_COMPLETE);
        }

    }
}
