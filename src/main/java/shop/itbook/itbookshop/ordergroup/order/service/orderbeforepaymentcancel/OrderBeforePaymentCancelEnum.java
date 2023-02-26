package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel;

import java.util.Arrays;
import java.util.Optional;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
public enum OrderBeforePaymentCancelEnum {

    구독비회원주문("nonMemberSubscriptionOrderBeforePaymentCancelProcessor"),
    구독회원주문("subscriptionOrderBeforePaymentCancelMemberService"),
    일반비회원주문("generalOrderBeforePaymentCancelNonMemberService"),
    일반회원주문("memberGeneralOrderBeforePaymentCancelProcessor");

    private String beanName;

    private static final OrderBeforePaymentCancelEnum[]
        ORDER_BEFORE_PAYMENT_FACTORY_ENUMS = OrderBeforePaymentCancelEnum.values();

    OrderBeforePaymentCancelEnum(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public static OrderBeforePaymentCancelEnum stringToOrderFactoryEnum(String orderType) {
        return Arrays.stream(ORDER_BEFORE_PAYMENT_FACTORY_ENUMS)
            .filter(orderFactoryEnum -> orderFactoryEnum.name().equals(orderType))
            .findFirst().orElseThrow(RuntimeException::new);
    }

    public static OrderBeforePaymentCancelEnum getOrderFactoryEnum(
        Optional<OrderMember> optionalOrderMember,
        Product product) {
        OrderBeforePaymentCancelEnum orderAferPaymentSuccessFactoryEnum;
        if (product.getIsSubscription()) {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelEnum.구독회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelEnum.구독비회원주문;
            }
        } else {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelEnum.일반회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelEnum.일반비회원주문;
            }
        }
        return orderAferPaymentSuccessFactoryEnum;
    }

}
