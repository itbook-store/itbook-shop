package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderAfterPaymentSuccessNonMemberService
    extends GeneralOrderAfterPaymentSuccessTemplate {
    GeneralOrderAfterPaymentSuccessNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        DeliveryService deliveryService,
        OrderProductService orderProductService) {
        super(orderStatusHistoryService, deliveryService, orderProductService);
    }

    @Override
    public Order processOrderAfterPaymentSuccess(Order order) {

        super.changeOrderStatus(order);
        super.changeDeliveryStatus(order);
        super.checkAndSetStock(order);

        return order;
    }
}
