package shop.itbook.itbookshop.deliverygroup.deliverystatushistory.dummy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
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

        testHistory.setDeliveryStatusHistoryNo(11L);
        testHistory.setDeliveryStatusCreatedAt(LocalDateTime.now());

        return testHistory;
    }
}