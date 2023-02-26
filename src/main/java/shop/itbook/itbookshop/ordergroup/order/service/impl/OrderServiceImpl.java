package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.processor.OrderAfterPaymentSuccessProcessor;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor.OrderBeforePaymentProcessor;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor.OrderBeforePaymentCancelProcessor;
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
    private final Map<String, OrderBeforePaymentProcessor> orderBeforePaymentProcessorMap;
    private final Map<String, OrderAfterPaymentSuccessProcessor>
        orderAfterPaymentSuccessProcessorMap;
    private final Map<String, OrderBeforePaymentCancelProcessor>
        orderBeforePaymentCancelProcessorMap;

    @Override
    @Transactional
    public OrderPaymentDto processOrderBeforePayment(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment,
        OrderBeforePaymentFactoryEnum orderBeforePaymentFactoryEnum) {

        OrderBeforePaymentProcessor orderBeforePaymentProcessor =
            orderBeforePaymentProcessorMap.get(orderBeforePaymentFactoryEnum.getBeanName());

        return orderBeforePaymentProcessor.processOrderBeforePayment(
            infoForProcessOrderBeforePayment);
    }

    @Override
    @Transactional
    public Order processOrderAfterPaymentSuccess(
        OrderAfterPaymentSuccessFactoryEnum orderAfterPaymentSuccessFactoryEnum, Long orderNo) {

        Order order = orderCrudService.findOrderEntity(orderNo);
        OrderAfterPaymentSuccessProcessor orderAfterPaymentSuccessProcessor =
            orderAfterPaymentSuccessProcessorMap.get(
                orderAfterPaymentSuccessFactoryEnum.getBeanName());

        return orderAfterPaymentSuccessProcessor.processOrderAfterPaymentSuccess(order);
    }

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(
        Long orderNo, OrderBeforePaymentCancelFactoryEnum orderBeforePaymentCancelFactoryEnum) {

        Order order = orderCrudService.findOrderEntity(orderNo);
        OrderBeforePaymentCancelProcessor orderBeforePaymentProcessor =
            orderBeforePaymentCancelProcessorMap.get(
                orderBeforePaymentCancelFactoryEnum.getBeanName());

        orderBeforePaymentProcessor.processOrderBeforePayment(order);
    }
}