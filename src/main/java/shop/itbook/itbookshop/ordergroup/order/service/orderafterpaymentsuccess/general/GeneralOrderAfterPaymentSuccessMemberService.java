package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply.MemberApplicableWhenOrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderAfterPaymentSuccessMemberService
    extends AbstractGeneralOrderAfterPaymentSuccess {

    private final MemberApplicableWhenOrderAfterPaymentSuccess memberApplicableWhenOrder;

    public GeneralOrderAfterPaymentSuccessMemberService(
        OrderStatusHistoryService orderStatusHistoryService, DeliveryService deliveryService,
        MemberApplicableWhenOrderAfterPaymentSuccess memberApplicableWhenOrder,
        OrderProductService orderProductService,
        MemberApplicableWhenOrderAfterPaymentSuccess memberApplicableWhenOrder1) {
        super(orderStatusHistoryService, deliveryService, memberApplicableWhenOrder,
            orderProductService);
        this.memberApplicableWhenOrder = memberApplicableWhenOrder1;
    }


    @Override
    public Order processOrderAfterPaymentSuccess(Order order) {

        super.changeOrderStatus(order);
        super.changeDeliveryStatus(order);
        super.checkAndSetStock(order);

        this.useCoupon(order);
        this.usePoint(order);

        return order;
    }

    private void useCoupon(Order order) {

        memberApplicableWhenOrder.useCoupon(order);
    }

    private void usePoint(Order order) {

        memberApplicableWhenOrder.usePoint(order);
    }
}
