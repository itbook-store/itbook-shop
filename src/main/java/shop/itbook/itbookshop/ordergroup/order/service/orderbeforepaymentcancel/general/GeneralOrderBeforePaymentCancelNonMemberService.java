package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderBeforePaymentCancelNonMemberService
    extends GeneralOrderBeforePaymentCancelTemplate {


    public GeneralOrderBeforePaymentCancelNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        OrderProductService orderProductService) {
        super(orderStatusHistoryService, orderProductService);
    }

    @Override
    protected void startUsageProcessing(Order order) {

    }
}
