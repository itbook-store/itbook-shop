package shop.itbook.itbookshop.ordergroup.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.service.factory.OrderFactory;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderAfterPaymentSuccessFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentCancelFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentFactoryEnum;

/**
 * OrderAdminService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderCrudService orderCrudService;

    /**
     * The Origin url.
     */
    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    public static final long BASE_AMOUNT_FOR_DELIVERY_FEE_CALC = 20000L;
    public static final long BASE_DELIVERY_FEE = 3000L;
    public final OrderFactory orderFactory;

    @Override
    @Transactional
    public OrderPaymentDto saveOrderBeforePaymentAndCreateOrderPaymentDto(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment,
        OrderBeforePaymentFactoryEnum orderBeforePaymentFactoryEnum) {

        OrderBeforePayment orderBeforePayment = orderFactory.getOrderBeforePayment(
            orderBeforePaymentFactoryEnum);
        return orderBeforePayment.processOrderBeforePayment(infoForProcessOrderBeforePayment);
    }

    @Override
    @Transactional
    public Order processAfterOrderPaymentSuccessRefactor(
        OrderAfterPaymentSuccessFactoryEnum orderAfterPaymentSuccessFactoryEnum, Long orderNo) {

        Order order = orderCrudService.findOrderEntity(orderNo);
        OrderAfterPaymentSuccess orderAfterPayment =
            orderFactory.getOrderAfterPaymentSuccess(orderAfterPaymentSuccessFactoryEnum);
        return orderAfterPayment.processOrderAfterPaymentSuccess(order);
    }

    @Override
    @Transactional
    public void processBeforeOrderCancelPaymentRefactor(
        Long orderNo, OrderBeforePaymentCancelFactoryEnum orderBeforePaymentCancelFactoryEnum) {

        Order order = orderCrudService.findOrderEntity(orderNo);
        OrderBeforePaymentCancel orderBeforePaymentCancel =
            orderFactory.getOrderBeforePaymentCancel(orderBeforePaymentCancelFactoryEnum);
        orderBeforePaymentCancel.cancel(order);
    }
}