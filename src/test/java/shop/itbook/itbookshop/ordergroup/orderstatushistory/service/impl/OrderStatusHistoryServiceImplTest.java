package shop.itbook.itbookshop.ordergroup.orderstatushistory.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.dummy.OrderStatusHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderStatusHistoryServiceImpl.class)
class OrderStatusHistoryServiceImplTest {

    @Autowired
    OrderStatusHistoryService orderStatusHistoryService;

    @MockBean
    OrderService orderService;

    @MockBean
    OrderStatusService orderStatusService;

    @MockBean
    OrderStatusHistoryRepository orderStatusHistoryRepository;

    @MockBean
    OrderStatusRepository orderStatusRepository;

    @MockBean
    OrderRepository orderRepository;

    Order dummyOrder;

    OrderStatus dummyOrderStatus;

    OrderStatusHistory dummyOrderStatusHistory;


    @BeforeEach
    void setUp() {

        dummyOrder = OrderDummy.getOrder();
        orderRepository.save(dummyOrder);

        dummyOrderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);

        dummyOrderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(dummyOrder, dummyOrderStatus);
    }

    @Test
    void addOrderStatusHistory() {

        given(orderStatusService.findByOrderStatusEnum(any())).willReturn(dummyOrderStatus);

        OrderStatus orderStatus =
            orderStatusService.findByOrderStatusEnum(OrderStatusEnum.PAYMENT_COMPLETE);

        OrderStatusHistory orderStatusHistory = dummyOrderStatusHistory;

        given(orderStatusHistoryRepository.save(any(OrderStatusHistory.class))).willReturn(
            orderStatusHistory);

        orderStatusHistoryService.addOrderStatusHistory(dummyOrder,
            orderStatus.getOrderStatusEnum());
    }

    @Test
    void save() {

        OrderStatusHistory orderStatusHistory = dummyOrderStatusHistory;

        orderStatusHistoryService.save(orderStatusHistory);
    }

    @Test
    void findOrderStatusHistoryByOrderNo() {
        given(orderStatusHistoryRepository.findFirstByOrder_OrderNoOrderByOrderStatusHistoryNoDesc(
            any())).willReturn(dummyOrderStatusHistory);

        assertThat(orderStatusHistoryService.findOrderStatusHistoryByOrderNo(1L)).isEqualTo(
            dummyOrderStatusHistory);
    }
}