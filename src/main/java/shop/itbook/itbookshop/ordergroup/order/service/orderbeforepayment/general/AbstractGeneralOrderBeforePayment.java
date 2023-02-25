package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.general;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractGeneralOrderBeforePayment implements OrderBeforePayment {

    private final OrderRepository orderRepository;

    private final OrderStatusHistoryService orderStatusHistoryService;


    protected void saveOrder(InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {
        // 주문 엔티티 인스턴스 생성 후 저장
        Order order =
            OrderTransfer.addDtoToEntity(infoForProcessOrderBeforePayment.getOrderAddRequestDto());
        orderRepository.save(order);

        infoForProcessOrderBeforePayment.setOrder(order);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAITING_FOR_PAYMENT);
    }

    protected abstract void saveOrderPerson(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment);


    protected abstract OrderPaymentDto calculateTotalAmount(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment);
}
