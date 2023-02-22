package shop.itbook.itbookshop.ordergroup.order.service.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderNonMemberService extends GeneralOrderBeforePaymentTemplate {

    public GeneralOrderNonMemberService(
        OrderRepository orderRepository,
        OrderStatusHistoryService orderStatusHistoryService) {
        super(orderRepository, orderStatusHistoryService);
    }

    @Override
    public void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess) {

    }

    @Override
    public OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        return null;
    }
}
