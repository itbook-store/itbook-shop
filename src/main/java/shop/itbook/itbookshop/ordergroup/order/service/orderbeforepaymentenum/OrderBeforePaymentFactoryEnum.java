package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum;

import java.util.Arrays;
import java.util.Optional;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
public enum OrderBeforePaymentFactoryEnum {

    구독비회원주문("subscriptionOrderBeforePaymentNonMemberService"),
    구독회원주문("subscriptionOrderBeforePaymentMemberService"),
    일반비회원주문("generalOrderBeforePaymentNonMemberService"),
    일반회원주문("generalOrderBeforePaymentMemberService");

    private String beanName;

    private static final OrderBeforePaymentFactoryEnum[]
        ORDER_BEFORE_PAYMENT_FACTORY_ENUMS = OrderBeforePaymentFactoryEnum.values();

    OrderBeforePaymentFactoryEnum(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public static OrderBeforePaymentFactoryEnum stringToOrderFactoryEnum(String orderType) {
        return Arrays.stream(ORDER_BEFORE_PAYMENT_FACTORY_ENUMS)
            .filter(orderFactoryEnum -> orderFactoryEnum.name().equals(orderType))
            .findFirst().orElseThrow(RuntimeException::new);
    }

    public static OrderBeforePaymentFactoryEnum getOrderFactoryEnum(
        Optional<OrderMember> optionalOrderMember,
        Product product) {
        OrderBeforePaymentFactoryEnum orderAferPaymentSuccessFactoryEnum;
        if (product.getIsSubscription()) {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentFactoryEnum.구독회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentFactoryEnum.구독비회원주문;
            }
        } else {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentFactoryEnum.일반회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentFactoryEnum.일반비회원주문;
            }
        }
        return orderAferPaymentSuccessFactoryEnum;
    }

}
