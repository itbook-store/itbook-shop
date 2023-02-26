package shop.itbook.itbookshop.ordergroup.order.service.base;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.결제전_처리전반에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.dto.response.결제_요청에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.processor.OrderAfterPaymentSuccessProcessor;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor.주문결제전처리기;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.processor.OrderBeforePaymentCancelProcessor;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccessEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancelEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePaymentEnum;

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
    private final Map<String, 주문결제전처리기> orderBeforePaymentProcessorMap;
    private final Map<String, OrderAfterPaymentSuccessProcessor>
        orderAfterPaymentSuccessProcessorMap;
    private final Map<String, OrderBeforePaymentCancelProcessor>
        orderBeforePaymentCancelProcessorMap;

    @Override
    @Transactional
    public 결제_요청에_필요한_정보_클래스 processOrderBeforePayment(
        결제전_처리전반에_필요한_정보_클래스 infoForProcessOrderBeforePayment,
        OrderBeforePaymentEnum orderBeforePaymentEnum) {

        주문결제전처리기 주문결제전처리기 =
            orderBeforePaymentProcessorMap.get(orderBeforePaymentEnum.getBeanName());

        결제_요청에_필요한_정보_클래스 orderPaymentDto = 주문결제전처리기.결제전_처리(
            infoForProcessOrderBeforePayment);

        return orderPaymentDto;
    }

    @Override
    @Transactional
    public Order processOrderAfterPaymentSuccess(
        OrderAfterPaymentSuccessEnum orderAfterPaymentSuccessEnum, Long orderNo) {

        Order order = orderCrudService.findOrderEntity(orderNo);
        OrderAfterPaymentSuccessProcessor orderAfterPaymentSuccessProcessor =
            orderAfterPaymentSuccessProcessorMap.get(
                orderAfterPaymentSuccessEnum.getBeanName());

        return orderAfterPaymentSuccessProcessor.processOrderAfterPaymentSuccess(order);
    }

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(
        Long orderNo, OrderBeforePaymentCancelEnum orderBeforePaymentCancelEnum) {

        Order order = orderCrudService.findOrderEntity(orderNo);
        OrderBeforePaymentCancelProcessor orderBeforePaymentCancelProcessor =
            orderBeforePaymentCancelProcessorMap.get(
                orderBeforePaymentCancelEnum.getBeanName());

        orderBeforePaymentCancelProcessor.processOrderBeforePaymentCancel(order);
    }
}