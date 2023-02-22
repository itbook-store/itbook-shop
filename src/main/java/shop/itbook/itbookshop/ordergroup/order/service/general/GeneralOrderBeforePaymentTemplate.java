package shop.itbook.itbookshop.ordergroup.order.service.general;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForPrePaymentProcess;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class GeneralOrderBeforePaymentTemplate implements OrderBeforePayment {

    private final OrderRepository orderRepository;

    private final OrderStatusHistoryService orderStatusHistoryService;


    @Override
    public OrderPaymentDto prePaymentProcess(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        this.saveOrderAndSub(infoForPrePaymentProcess);
        this.saveOrderPerson(infoForPrePaymentProcess);
        this.saveOrderProduct();
        return this.calculateTotalAmount(infoForPrePaymentProcess);
    }

    @Override
    public void saveOrderAndSub(InfoForPrePaymentProcess infoForPrePaymentProcess) {
        // 주문 엔티티 인스턴스 생성 후 저장
        Order order =
            OrderTransfer.addDtoToEntity(infoForPrePaymentProcess.getOrderAddRequestDto());
        orderRepository.save(order);

        infoForPrePaymentProcess.setOrder(order);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAITING_FOR_PAYMENT);
    }

    @Override
    public void saveOrderProduct() {

    }

    @Override
    public abstract void saveOrderPerson(InfoForPrePaymentProcess infoForPrePaymentProcess);

    @Override
    public abstract OrderPaymentDto calculateTotalAmount(
        InfoForPrePaymentProcess infoForPrePaymentProcess);
}
