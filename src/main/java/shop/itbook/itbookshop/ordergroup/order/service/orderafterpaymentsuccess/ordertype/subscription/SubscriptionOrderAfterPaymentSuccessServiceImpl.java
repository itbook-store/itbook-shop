package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class SubscriptionOrderAfterPaymentSuccessServiceImpl implements
    OrderAfterPaymentSuccessServiceAboutOrderType {

    private final OrderStatusHistoryService orderStatusHistoryService;

    @Override
    public Order processAboutOrderType(Order order) {
        this.changeOrderStatus(order);
        return order;
    }

    protected void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);
    }
}
