package shop.itbook.itbookshop.ordergroup.ordersubscription.service.impl;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.service.OrderSubscriptionService;

/**
 * OrderSubscriptionService 인터페이스의 기본 구현체입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderSubscriptionServiceImpl implements OrderSubscriptionService {

    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderRepository orderRepository;

    @Override
    public void registerOrderSubscription(Order order, Integer subscriptionPeriod) {

        int sequence = 1;

        while (sequence <= subscriptionPeriod) {

            OrderSubscription orderSubscription = OrderSubscription.builder()
                .order(order)
                .subscriptionStartDate(LocalDate.now().plusMonths(1).withDayOfMonth(1))
                .sequence(sequence)
                .subscriptionPeriod(subscriptionPeriod)
                .build();
            orderSubscriptionRepository.save(orderSubscription);
            sequence++;
        }
    }
}
