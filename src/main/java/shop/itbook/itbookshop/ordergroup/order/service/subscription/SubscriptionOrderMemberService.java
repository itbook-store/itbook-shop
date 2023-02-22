package shop.itbook.itbookshop.ordergroup.order.service.subscription;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderMemberService extends SubscriptionOrderBeforePaymentTemplate {
    @Override
    public void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess) {


    }

    @Override
    public void saveOrderAndSub(InfoForPrePaymentProcess infoForPrePaymentProcess) {

    }

    @Override
    public OrderPaymentDto calculateTotalAmount(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        return null;
    }
}
