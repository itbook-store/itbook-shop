package shop.itbook.itbookshop.ordergroup.order.service.impl;

import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderAfterPaymentSuccessFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentCancelFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentFactoryEnum;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {

    OrderPaymentDto saveOrderBeforePaymentAndCreateOrderPaymentDto(
        InfoForPrePaymentProcess infoForPrePaymentProcess,
        OrderBeforePaymentFactoryEnum orderBeforePaymentFactoryEnum);

    @Transactional
    Order processAfterOrderPaymentSuccessRefactor(
        OrderAfterPaymentSuccessFactoryEnum orderAfterPaymentSuccessFactoryEnum, Long orderNo);

    @Transactional
    void processBeforeOrderCancelPaymentRefactor(Long orderNo,
                                                 OrderBeforePaymentCancelFactoryEnum orderBeforePaymentCancelFactoryEnum);
}
