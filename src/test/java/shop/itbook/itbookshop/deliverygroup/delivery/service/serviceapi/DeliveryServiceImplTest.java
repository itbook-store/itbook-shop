package shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.impl.DeliveryServiceImpl;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.DeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.dummy.DeliveryStatusDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.dummy.DeliveryStatusHistoryDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 강명관
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(DeliveryServiceImpl.class)
public class DeliveryServiceImplTest {

    @Autowired
    DeliveryService deliveryService;

    @MockBean
    OrderRepository orderRepository;
    @MockBean
    DeliveryRepository deliveryRepository;

    @MockBean
    DeliveryStatusRepository deliveryStatusRepository;

    @MockBean
    DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;

    @MockBean
    OrderStatusHistoryService orderStatusHistoryService;

    @MockBean
    OrderStatusHistoryRepository orderStatusHistoryRepository;

    @MockBean
    OrderStatusRepository orderStatusRepository;

    Order order;

    Delivery delivery;

    DeliveryStatus deliveryStatus;

    DeliveryStatusHistory deliveryStatusHistory;

    OrderStatusHistory dummyOrderStatusHistory;

    OrderStatus dummyOrderStatus;


    @BeforeEach
    void setUp() {

        order = OrderDummy.getOrder();
        delivery = DeliveryDummy.createDelivery(order);
        deliveryStatus = DeliveryStatusDummy.getDummyWait();
        deliveryStatusHistory = DeliveryStatusHistoryDummy.getDeliveryStatusHistory();
        dummyOrderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.DELIVERY_COMPLETED);
    }

    @DisplayName("registerDelivery 배송 등록 && 주문의 상태이력 배송대기 상태로 추가 테스트")
    @Test
    void registerDeliveryTest() {
        // given
        given(deliveryRepository.save(delivery)).willReturn(delivery);
        given(deliveryStatusRepository.findByDeliveryStatusEnum(DeliveryStatusEnum.WAIT_DELIVERY))
            .willReturn(Optional.of(deliveryStatus));

        deliveryStatusHistory.setDelivery(delivery);

        // when
        given(deliveryStatusHistoryRepository.save(deliveryStatusHistory))
            .willReturn(deliveryStatusHistory);

        // then
        assertThat(deliveryStatusHistory.getDelivery().getDeliveryNo())
            .isEqualTo(delivery.getDeliveryNo());

        assertThat(deliveryStatusHistory.getDeliveryStatus().getDeliveryStatusEnum())
            .isEqualTo(deliveryStatus.getDeliveryStatusEnum());

        deliveryService.registerDelivery(order);

    }

    @Test
    void findTrackingNoByOrderNo() {
        orderRepository.save(order);
        delivery = DeliveryDummy.createDelivery(order);

        given(deliveryRepository.save(delivery)).willReturn(delivery);
        given(deliveryStatusRepository.findByDeliveryStatusEnum(DeliveryStatusEnum.WAIT_DELIVERY))
            .willReturn(Optional.of(deliveryStatus));

        deliveryStatusHistory.setDelivery(delivery);
        
        given(deliveryService.findTrackingNoByOrderNo(any())).willReturn(delivery.getTrackingNo());

        assertThat(
            deliveryService.findTrackingNoByOrderNo(delivery.getOrder().getOrderNo())).isEqualTo(
            delivery.getTrackingNo());
    }

    @Test
    void completeDelivery() {
        deliveryStatusHistory = DeliveryStatusHistoryDummy.getDeliveryStatusHistory2();

        // given
        given(deliveryRepository.save(delivery)).willReturn(delivery);
        given(deliveryRepository.findById(delivery.getDeliveryNo())).willReturn(
            Optional.ofNullable(delivery));

        Delivery testDelivery = deliveryRepository.findById(delivery.getDeliveryNo()).orElseThrow();

        deliveryStatus = new DeliveryStatus(2, DeliveryStatusEnum.DELIVERY_COMPLETED);
        given(deliveryStatusRepository.save(deliveryStatus)).willReturn(deliveryStatus);
        given(deliveryStatusRepository.findByDeliveryStatusEnum(
            deliveryStatus.getDeliveryStatusEnum()))
            .willReturn(Optional.of(deliveryStatus));

        given(orderRepository.save(order)).willReturn(order);
        given(orderRepository.findOrderByDeliveryNo(any())).willReturn(order);
        Order testOrder = orderRepository.findOrderByDeliveryNo(testDelivery.getDeliveryNo());
        testDelivery.setOrder(testOrder);

        deliveryStatusHistory.setDelivery(testDelivery);


        // when
        given(deliveryStatusHistoryRepository.save(deliveryStatusHistory))
            .willReturn(deliveryStatusHistory);

        // then
        assertThat(deliveryStatusHistory.getDelivery().getDeliveryNo())
            .isEqualTo(testDelivery.getDeliveryNo());

        assertThat(deliveryStatusHistory.getDeliveryStatus().getDeliveryStatusEnum())
            .isEqualTo(deliveryStatus.getDeliveryStatusEnum());

        deliveryService.completeDelivery(testDelivery.getDeliveryNo());
    }
}
