package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.general;

import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class GeneralOrderBeforePaymentCancelMemberService
    extends GeneralOrderBeforePaymentCancelTemplate {
    @Override
    protected void startUsageProcessing(Order order) {

    }
}
