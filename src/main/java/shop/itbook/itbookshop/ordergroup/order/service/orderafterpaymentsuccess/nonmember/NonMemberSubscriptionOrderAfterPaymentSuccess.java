package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.nonmember;

import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
public class NonMemberSubscriptionOrderAfterPaymentSuccess
    extends AbstractNonMemberOrderAfterPaymentSuccess {

    public NonMemberSubscriptionOrderAfterPaymentSuccess(
        OrderStatusHistoryService orderStatusHistoryService,
        DeliveryService deliveryService,
        OrderProductService orderProductService) {
        super(orderStatusHistoryService, deliveryService, orderProductService);
    }

    @Override
    protected void processOrderAfterPaymentSuccessWithoutNonMemberProcessing(Order order) {

    }
}
