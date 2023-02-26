package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePaymentServiceAboutOrderType {
    void processAboutOrderType(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment);
}
