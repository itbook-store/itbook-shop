package shop.itbook.itbookshop.ordergroup.ordersubscription.service;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 구독 관련 비즈니스 로직을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderSubscriptionService {
    void registerOrderSubscription(Order order, Integer subscriptionPeriod);

    /**
     * 구독 주문 인지 검사합니다.
     *
     * @param orderNo the order no
     * @return 구독이면 true 아니면 false
     * @author 정재원
     */
    boolean isSubscription(Long orderNo);
}
