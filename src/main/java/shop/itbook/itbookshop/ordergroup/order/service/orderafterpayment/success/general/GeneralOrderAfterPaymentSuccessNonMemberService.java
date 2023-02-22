package shop.itbook.itbookshop.ordergroup.order.service.orderafterpayment.success.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderAfterPaymentSuccessNonMemberService
    extends GeneralOrderAfterPaymentSuccessTemplate {
    public GeneralOrderAfterPaymentSuccessNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        DeliveryService deliveryService) {
        super(orderStatusHistoryService, deliveryService);
    }

    @Override
    protected void startUsageProcessing() {

    }
}
