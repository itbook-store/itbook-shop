package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePayment {

    OrderPaymentDto processOrderBeforePayment(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment);

}
