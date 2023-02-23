package shop.itbook.itbookshop.ordergroup.order.service.factory;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderAfterPaymentSuccessFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentCancelFactoryEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentFactoryEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final Map<String, OrderBeforePayment> orderBeforePaymentMap;
    private final Map<String, OrderAfterPaymentSuccess> orderAfterPaymentSuccessMap;
    private final Map<String, OrderBeforePaymentCancel> orderBeforePaymentCancelMap;

    public OrderBeforePayment getOrderBeforePayment(
        OrderBeforePaymentFactoryEnum orderBeforePaymentFactoryEnum) {
        return orderBeforePaymentMap.get(orderBeforePaymentFactoryEnum.getBeanName());
    }


    public OrderAfterPaymentSuccess getOrderAfterPaymentSuccess(
        OrderAfterPaymentSuccessFactoryEnum orderAfterPaymentSuccessFactoryEnum) {
        return orderAfterPaymentSuccessMap.get(orderAfterPaymentSuccessFactoryEnum.getBeanName());
    }

    public OrderBeforePaymentCancel getOrderBeforePaymentCancel(
        OrderBeforePaymentCancelFactoryEnum orderBeforePaymentCancelFactoryEnum) {
        return orderBeforePaymentCancelMap.get(orderBeforePaymentCancelFactoryEnum.getBeanName());
    }
}
