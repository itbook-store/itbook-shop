package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.general;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccessEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePaymentEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.OrderBeforePaymentServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GeneralOrderBeforePaymentServiceImpl
    implements OrderBeforePaymentServiceAboutOrderType {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;

    @Override
    @Transactional
    public void processAboutOrderType(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {
        this.saveOrder(infoForProcessOrderBeforePayment);

        infoForProcessOrderBeforePayment.setOrderType("일반");
    }

    private void saveOrder(InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {
        // 주문 엔티티 인스턴스 생성 후 저장
        Order order =
            OrderTransfer.addDtoToEntity(infoForProcessOrderBeforePayment.getOrderAddRequestDto());
        orderRepository.save(order);

        infoForProcessOrderBeforePayment.setOrder(order);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAITING_FOR_PAYMENT);
    }
}
