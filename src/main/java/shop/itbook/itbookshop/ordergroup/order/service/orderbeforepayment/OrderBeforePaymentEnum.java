package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment;

import java.util.Arrays;
import java.util.Optional;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
public enum OrderBeforePaymentEnum {

    구독비회원주문("nonMemberSubscriptionOrderBeforePaymentProcessor"),
    구독회원주문("memberSubscriptionOrderBeforePaymentProcessor"),
    일반비회원주문("nonMemberGeneralOrderBeforePaymentProcessor"),
    일반회원주문("memberGeneralOrderBeforePaymentProcessor");

    private String beanName;

    private static final OrderBeforePaymentEnum[]
        ORDER_BEFORE_PAYMENT_FACTORY_ENUMS = OrderBeforePaymentEnum.values();

    OrderBeforePaymentEnum(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public static OrderBeforePaymentEnum stringToOrderFactoryEnum(String orderType) {
        return Arrays.stream(ORDER_BEFORE_PAYMENT_FACTORY_ENUMS)
            .filter(orderFactoryEnum -> orderFactoryEnum.name().equals(orderType))
            .findFirst().orElseThrow(RuntimeException::new);
    }

    public static OrderBeforePaymentEnum getOrderFactoryEnum(
        Optional<OrderMember> optionalOrderMember,
        Product product) {
        OrderBeforePaymentEnum orderAferPaymentSuccessFactoryEnum;
        if (product.getIsSubscription()) {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentEnum.구독회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentEnum.구독비회원주문;
            }
        } else {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentEnum.일반회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentEnum.일반비회원주문;
            }
        }
        return orderAferPaymentSuccessFactoryEnum;
    }

}
