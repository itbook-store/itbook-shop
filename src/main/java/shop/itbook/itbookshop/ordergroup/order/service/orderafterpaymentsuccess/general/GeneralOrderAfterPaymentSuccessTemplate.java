package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.general;

import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class GeneralOrderAfterPaymentSuccessTemplate implements OrderAfterPaymentSuccess {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final DeliveryService deliveryService;

    @Override
    public Order success(Order order) {

        this.changeOrderStatus(order);
        this.changeDeliveryStatus(order);
        this.startUsageProcessing(order);
        return order;
    }

    @Override
    public void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAIT_DELIVERY);
    }

    private void changeDeliveryStatus(Order order) {
        deliveryService.registerDelivery(order);
    }

    protected abstract void startUsageProcessing(Order order);
}
