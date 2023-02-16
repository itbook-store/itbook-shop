package shop.itbook.itbookshop.ordergroup.order.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void paymentCompleteSubscriptionOrderStatusChangeToWaitDelivery() {
        orderService.addOrderStatusHistorySubscriptionProductDeliveryWait();
    }
}
