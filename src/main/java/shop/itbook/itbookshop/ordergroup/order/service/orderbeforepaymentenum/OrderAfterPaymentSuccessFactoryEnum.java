package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum;

import java.util.Arrays;
import java.util.Optional;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
public enum OrderAfterPaymentSuccessFactoryEnum {

    구독비회원주문("subscriptionOrderAfterPaymentSuccessNonMemberService"),
    구독회원주문("subscriptionOrderAfterPaymentSuccessMemberService"),
    일반비회원주문("generalOrderAfterPaymentSuccessNonMemberService"),
    일반회원주문("generalOrderAfterPaymentSuccessMemberService");

    private String beanName;

    private static final OrderAfterPaymentSuccessFactoryEnum[]
        ORDER_AFTER_PAYMENT_SUCCESS_FACTORY_ENUMS = OrderAfterPaymentSuccessFactoryEnum.values();

    OrderAfterPaymentSuccessFactoryEnum(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public static OrderAfterPaymentSuccessFactoryEnum stringToOrderFactoryEnum(String orderType) {
        return Arrays.stream(ORDER_AFTER_PAYMENT_SUCCESS_FACTORY_ENUMS)
            .filter(orderFactoryEnum -> orderFactoryEnum.name().equals(orderType))
            .findFirst().orElseThrow(RuntimeException::new);
    }

    public static OrderAfterPaymentSuccessFactoryEnum getOrderFactoryEnum(
        Optional<OrderMember> optionalOrderMember,
        Product product) {
        OrderAfterPaymentSuccessFactoryEnum orderAfterPaymentSuccessFactoryEnum;
        if (product.getIsSubscription()) {
            if (optionalOrderMember.isPresent()) {
                orderAfterPaymentSuccessFactoryEnum = OrderAfterPaymentSuccessFactoryEnum.구독회원주문;
            } else {
                orderAfterPaymentSuccessFactoryEnum = OrderAfterPaymentSuccessFactoryEnum.구독비회원주문;
            }
        } else {
            if (optionalOrderMember.isPresent()) {
                orderAfterPaymentSuccessFactoryEnum = OrderAfterPaymentSuccessFactoryEnum.일반회원주문;
            } else {
                orderAfterPaymentSuccessFactoryEnum = OrderAfterPaymentSuccessFactoryEnum.일반비회원주문;
            }
        }
        return orderAfterPaymentSuccessFactoryEnum;
    }

}
