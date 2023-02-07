package shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.DeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.exception.DeliveryStatusNotFoundException;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * DeliveryService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;

    @Override
    @Transactional
    public void registerDelivery(Order order) {

        StringBuilder stringBuilder = new StringBuilder();

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        deliveryRepository.save(delivery);

        DeliveryStatus deliveryStatus =
            deliveryStatusRepository.findByDeliveryStatusEnum(DeliveryStatusEnum.WAIT_DELIVERY)
                .orElseThrow(
                    DeliveryStatusNotFoundException::new);

        DeliveryStatusHistory deliveryStatusHistory = new DeliveryStatusHistory(
            delivery,
            stringBuilder
                .append(order.getRoadNameAddress())
                .append(" ").append(order.getRecipientAddressDetails())
                .toString(),
            deliveryStatus);

        deliveryStatusHistoryRepository.save(deliveryStatusHistory);
    }
}
