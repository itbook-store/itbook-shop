package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum;

import java.util.Arrays;
import java.util.Optional;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
public enum OrderBeforePaymentCancelFactoryEnum {

    구독비회원주문("subscriptionOrderBeforePaymentCancelNonMemberService"),
    구독회원주문("subscriptionOrderBeforePaymentCancelMemberService"),
    일반비회원주문("generalOrderBeforePaymentCancelNonMemberService"),
    일반회원주문("generalOrderBeforePaymentCancelMemberService");

    private String beanName;

    private static final OrderBeforePaymentCancelFactoryEnum[]
        ORDER_BEFORE_PAYMENT_FACTORY_ENUMS = OrderBeforePaymentCancelFactoryEnum.values();

    OrderBeforePaymentCancelFactoryEnum(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public static OrderBeforePaymentCancelFactoryEnum stringToOrderFactoryEnum(String orderType) {
        return Arrays.stream(ORDER_BEFORE_PAYMENT_FACTORY_ENUMS)
            .filter(orderFactoryEnum -> orderFactoryEnum.name().equals(orderType))
            .findFirst().orElseThrow(RuntimeException::new);
    }

    public static OrderBeforePaymentCancelFactoryEnum getOrderFactoryEnum(
        Optional<OrderMember> optionalOrderMember,
        Product product) {
        OrderBeforePaymentCancelFactoryEnum orderAferPaymentSuccessFactoryEnum;
        if (product.getIsSubscription()) {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelFactoryEnum.구독회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelFactoryEnum.구독비회원주문;
            }
        } else {
            if (optionalOrderMember.isPresent()) {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelFactoryEnum.일반회원주문;
            } else {
                orderAferPaymentSuccessFactoryEnum = OrderBeforePaymentCancelFactoryEnum.일반비회원주문;
            }
        }
        return orderAferPaymentSuccessFactoryEnum;
    }

}
