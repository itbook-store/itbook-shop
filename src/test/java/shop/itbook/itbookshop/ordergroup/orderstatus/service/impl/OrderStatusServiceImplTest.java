package shop.itbook.itbookshop.ordergroup.orderstatus.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.exception.OrderStatusNotFoundException;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderStatusServiceImpl.class)
class OrderStatusServiceImplTest {

    @Autowired
    OrderStatusService orderStatusService;

    @MockBean
    OrderStatusRepository orderStatusRepository;

    OrderStatus dummyOrderStatus;

    @BeforeEach
    void setUp() {
        dummyOrderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
    }

    @Test
    void findByOrderStatusEnum() {

        given(orderStatusRepository.findByOrderStatusEnum(any())).willReturn(
            Optional.ofNullable(dummyOrderStatus));

        assertThat(orderStatusService.findByOrderStatusEnum(OrderStatusEnum.PAYMENT_COMPLETE))
            .isEqualTo(dummyOrderStatus);
    }

    @Test
    void findByOrderStatusEnumFail() {
        given(orderStatusRepository.findByOrderStatusEnum(any())).willThrow(
            OrderStatusNotFoundException.class);

        assertThatThrownBy(() -> orderStatusService.findByOrderStatusEnum(OrderStatusEnum.CANCELED))
            .isInstanceOf(OrderStatusNotFoundException.class);
    }
}