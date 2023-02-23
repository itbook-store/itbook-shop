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
//        if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.구독회원주문)) {
//            return orderBeforePaymentMap.get("subscriptionOrderBeforePaymentMemberService");
//        } else if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.구독비회원주문)) {
//            return orderBeforePaymentMap.get("subscriptionOrderBeforePaymentNonMemberService");
//        } else if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.일반회원주문)) {
//            return orderBeforePaymentMap.get("generalOrderBeforePaymentMemberService");
//        } else if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.일반비회원주문)) {
//            return orderBeforePaymentMap.get("generalOrderBeforePaymentNonMemberService");
//        }
//
//        throw new RuntimeException("구현체가 존재하지 않습니다. type : " +
//            orderAfterPaymentSuccessFactoryEnum);
        return orderBeforePaymentMap.get(orderBeforePaymentFactoryEnum.getBeanName());
    }


    public OrderAfterPaymentSuccess getOrderAfterPaymentSuccess(
        OrderAfterPaymentSuccessFactoryEnum orderAfterPaymentSuccessFactoryEnum) {


//        if (orderFactoryEnum.equals(OrderFactoryEnum.구독회원주문)) {
//            return orderAfterPaymentSuccessMap.get(
//                "subscriptionOrderAfterPaymentSuccessMemberService");
//        } else if (orderFactoryEnum.equals(OrderFactoryEnum.구독비회원주문)) {
//            return orderAfterPaymentSuccessMap.get(
//                "subscriptionOrderAfterPaymentSuccessNonMemberService");
//        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반회원주문)) {
//            return orderAfterPaymentSuccessMap.get("subscriptionOrderAfterPaymentSuccessNonMemberService");
//        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반비회원주문)) {
//            return orderAfterPaymentSuccessMap.get(
//                "generalOrderAfterPaymentSuccessNonMemberService");
//        }

        return orderAfterPaymentSuccessMap.get(orderAfterPaymentSuccessFactoryEnum.getBeanName());
    }

    public OrderBeforePaymentCancel getOrderBeforePaymentCancel(
        OrderBeforePaymentCancelFactoryEnum orderBeforePaymentCancelFactoryEnum) {

//        if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.구독회원주문)) {
//            return orderBeforePaymentCancelMap.get(
//                "subscriptionOrderBeforePaymentCancelMemberService");
//        } else if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.구독비회원주문)) {
//            return orderBeforePaymentCancelMap.get(
//                "subscriptionOrderBeforePaymentCancelNonMemberService");
//        } else if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.일반회원주문)) {
//            return orderBeforePaymentCancelMap.get("generalOrderBeforePaymentCancelMemberService");
//        } else if (orderAfterPaymentSuccessFactoryEnum.equals(
//            OrderAfterPaymentSuccessFactoryEnum.일반비회원주문)) {
//            return orderBeforePaymentCancelMap.get(
//                "generalOrderBeforePaymentCancelNonMemberService");
//        }
//
//        throw new RuntimeException("구현체가 존재하지 않습니다. type : " +
//            orderAfterPaymentSuccessFactoryEnum);
        return orderBeforePaymentCancelMap.get(orderBeforePaymentCancelFactoryEnum.getBeanName());

    }
}
