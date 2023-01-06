package shop.itbook.itbookshop.subscriptionorderstatusenum;

import lombok.Getter;

/**
 * 구독주문 상태 Enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum SubscriptionOrderStatusEnum {

    SUBSCRIBING("구독중"),
    SUBSCRIPTION_EXPIRED("구독만료");

    private final String subscriptionOrderStatus;

    SubscriptionOrderStatusEnum(String subscriptionOrderStatus) {
        this.subscriptionOrderStatus = subscriptionOrderStatus;
    }
}
