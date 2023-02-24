package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.memberapply.DefaultMemberApplyWhenOrder;
import shop.itbook.itbookshop.ordergroup.order.service.memberapply.MemberApplicableWhenOrder;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderAfterPaymentSuccessMemberService
    extends GeneralOrderAfterPaymentSuccessTemplate {

    private final MemberApplicableWhenOrder memberApplicableWhenOrder;

    GeneralOrderAfterPaymentSuccessMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        DeliveryService deliveryService,
        OrderProductService orderProductService,
        DefaultMemberApplyWhenOrder memberApplicableWhenOrder) {

        super(orderStatusHistoryService, deliveryService, orderProductService);
        this.memberApplicableWhenOrder = memberApplicableWhenOrder;
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
