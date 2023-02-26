package shop.itbook.itbookshop.deliverygroup.deliverystatushistory.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.dummy.DeliveryStatusDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;

/**
 * @author 정재원
 * @since 1.0
 */
public class DeliveryStatusHistoryDummy {
    public static DeliveryStatusHistory getDeliveryStatusHistory() {
        DeliveryStatusHistory testHistory = DeliveryStatusHistory.builder()
            .delivery(null)
            .historyLocation("testHistoryLocation")
            .deliveryStatus(DeliveryStatusDummy.getDummyWait())
            .build();

        testHistory.setDeliveryStatusCreatedAt(LocalDateTime.now());

        return testHistory;
    }

    public static DeliveryStatusHistory getDeliveryStatusHistory2() {
        DeliveryStatusHistory testHistory = DeliveryStatusHistory.builder()
            .delivery(null)
            .historyLocation("testHistoryLocation")
            .deliveryStatus(DeliveryStatusDummy.getDummyCompleted())
            .build();

        testHistory.setDeliveryStatusCreatedAt(LocalDateTime.now());

        return testHistory;
    }
}