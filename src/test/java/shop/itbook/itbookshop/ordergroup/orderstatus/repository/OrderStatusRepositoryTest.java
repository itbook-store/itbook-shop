package shop.itbook.itbookshop.ordergroup.orderstatus.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderStatusRepositoryTest {

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Test
    @DisplayName("주문 상태 테이블에 저장 성공")
    void saveSuccessTest() {
        OrderStatusEnum waitingEnum = OrderStatusEnum.WAITING_FOR_PAYMENT;

        OrderStatus orderStatus =
            OrderStatusDummy.createByEnum(waitingEnum);

        OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);

        assertThat(savedOrderStatus.getOrderStatusEnum().getOrderStatus()).isEqualTo(
            waitingEnum.getOrderStatus());
    }
}