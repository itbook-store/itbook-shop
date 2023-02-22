package shop.itbook.itbookshop.ordergroup.order.service.factory;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderFactoryEnum;

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

    public OrderBeforePayment getOrderBeforePayment(OrderFactoryEnum orderFactoryEnum) {
        if (orderFactoryEnum.equals(OrderFactoryEnum.구독회원주문)) {
            return orderBeforePaymentMap.get("subscriptionOrderBeforePaymentMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.구독비회원주문)) {
            return orderBeforePaymentMap.get("subscriptionOrderBeforePaymentNonMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반회원주문)) {
            return orderBeforePaymentMap.get("generalOrderBeforePaymentMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반비회원주문)) {
            return orderBeforePaymentMap.get("generalOrderBeforePaymentNonMemberService");
        }

        throw new RuntimeException("구현체가 존재하지 않습니다. type : " +
            orderFactoryEnum);
    }


    public OrderAfterPaymentSuccess getOrderAfterPaymentSuccess(OrderFactoryEnum orderFactoryEnum) {

        if (orderFactoryEnum.equals(OrderFactoryEnum.구독회원주문)) {
            return orderAfterPaymentSuccessMap.get(
                "subscriptionOrderAfterPaymentSuccessMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.구독비회원주문)) {
            return orderAfterPaymentSuccessMap.get(
                "subscriptionOrderAfterPaymentSuccessNonMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반회원주문)) {
            return orderAfterPaymentSuccessMap.get("generalOrderAfterPaymentSuccessMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반비회원주문)) {
            return orderAfterPaymentSuccessMap.get(
                "generalOrderAfterPaymentSuccessNonMemberService");
        }

        throw new RuntimeException("구현체가 존재하지 않습니다. type : " +
            orderFactoryEnum);
    }

    public OrderBeforePaymentCancel getOrderBeforePaymentCancel(OrderFactoryEnum orderFactoryEnum) {

        if (orderFactoryEnum.equals(OrderFactoryEnum.구독회원주문)) {
            return orderBeforePaymentCancelMap.get(
                "subscriptionOrderBeforePaymentCancelMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.구독비회원주문)) {
            return orderBeforePaymentCancelMap.get(
                "subscriptionOrderBeforePaymentCancelNonMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반회원주문)) {
            return orderBeforePaymentCancelMap.get("generalOrderBeforePaymentCancelMemberService");
        } else if (orderFactoryEnum.equals(OrderFactoryEnum.일반비회원주문)) {
            return orderBeforePaymentCancelMap.get(
                "generalOrderBeforePaymentCancelNonMemberService");
        }

        throw new RuntimeException("구현체가 존재하지 않습니다. type : " +
            orderFactoryEnum);
    }
}
