package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.general;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderBeforePaymentCancelNonMemberService
    extends AbstractGeneralOrderBeforePaymentCancel {

    public GeneralOrderBeforePaymentCancelNonMemberService(
        OrderStatusHistoryService orderStatusHistoryService) {
        super(orderStatusHistoryService);
    }

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(Order order) {

        super.checkOrderStatus(order.getOrderNo());
        super.changeOrderStatusAboutOrderCancel(order);
    }
}
