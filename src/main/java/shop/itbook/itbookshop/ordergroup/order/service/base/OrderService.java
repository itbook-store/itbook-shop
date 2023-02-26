package shop.itbook.itbookshop.ordergroup.order.service.base;

import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccessEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancelEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePaymentEnum;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {

    OrderPaymentDto processOrderBeforePayment(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment,
        OrderBeforePaymentEnum orderBeforePaymentEnum);

    @Transactional
    Order processOrderAfterPaymentSuccess(
        OrderAfterPaymentSuccessEnum orderAfterPaymentSuccessEnum, Long orderNo);

    @Transactional
    void processOrderBeforePaymentCancel(Long orderNo,
                                         OrderBeforePaymentCancelEnum orderBeforePaymentCancelEnum);
}
