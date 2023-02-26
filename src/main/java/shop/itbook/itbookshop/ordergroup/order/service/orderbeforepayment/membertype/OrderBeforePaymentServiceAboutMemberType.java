package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype;

import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePaymentServiceAboutMemberType {
    OrderPaymentDto processAboutMemberType(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment);
}
