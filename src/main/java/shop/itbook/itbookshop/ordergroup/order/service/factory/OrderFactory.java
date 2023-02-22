package shop.itbook.itbookshop.ordergroup.order.service.factory;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.orderbeforepaymentenum.OrderBeforePaymentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final Map<String, OrderBeforePayment> orderFactoryMap;

    public OrderBeforePayment getOrderBeforePayment(OrderBeforePaymentEnum orderBeforePaymentEnum) {
        if (orderBeforePaymentEnum.equals(OrderBeforePaymentEnum.구독회원주문)) {
            return orderFactoryMap.get("subscriptionOrderMemberService");
        } else if (orderBeforePaymentEnum.equals(OrderBeforePaymentEnum.구독비회원주문)) {
            return orderFactoryMap.get("subscriptionOrderNonMemberService");
        } else if (orderBeforePaymentEnum.equals(OrderBeforePaymentEnum.일반회원주문)) {
            return orderFactoryMap.get("generalOrderMemberService");
        } else if (orderBeforePaymentEnum.equals(OrderBeforePaymentEnum.일반비회원주문)) {
            return orderFactoryMap.get("generalOrderNonMemberService");
        }

        throw new RuntimeException("OrderBeforePayment 구현체가 존재하지 않습니다. type : " +
            orderBeforePaymentEnum);
    }


}
