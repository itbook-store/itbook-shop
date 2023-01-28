package shop.itbook.itbookshop.deliverygroup.delivery.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.CustomDeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.QDeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.QDeliveryStatusHistory;

/**
 * CustomDeliveryRepository 의 구현 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public class DeliveryRepositoryImpl extends QuerydslRepositorySupport implements
    CustomDeliveryRepository {

    public DeliveryRepositoryImpl() {
        super(Delivery.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeliveryWithStatusResponseDto> findDeliveryListWithStatus() {
        QDelivery delivery = QDelivery.delivery;
        QDeliveryStatusHistory deliveryStatusHistory = QDeliveryStatusHistory.deliveryStatusHistory;
        QDeliveryStatus deliveryStatus = QDeliveryStatus.deliveryStatus;

        return from(delivery)
            .innerJoin(deliveryStatusHistory)
            .on(delivery.deliveryNo.eq(deliveryStatusHistory.delivery.deliveryNo))
            .fetchJoin()
            .innerJoin(deliveryStatus)
            .on(deliveryStatusHistory.deliveryStatus.eq(deliveryStatus))
            .fetchJoin()
            .select(Projections.fields(DeliveryWithStatusResponseDto.class,
                delivery.deliveryNo,
                delivery.order.orderNo,
                delivery.trackingNo,
                deliveryStatus.deliveryStatusEnum.stringValue().as("deliveryStatus")))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait() {
        QDelivery delivery = QDelivery.delivery;
        QDeliveryStatusHistory deliveryStatusHistory = QDeliveryStatusHistory.deliveryStatusHistory;
        QDeliveryStatus deliveryStatus = QDeliveryStatus.deliveryStatus;
        
        return from(delivery)
            .innerJoin(deliveryStatusHistory)
            .on(delivery.deliveryNo.eq(deliveryStatusHistory.delivery.deliveryNo))
            .fetchJoin()
            .innerJoin(deliveryStatus)
            .on(deliveryStatusHistory.deliveryStatus.eq(deliveryStatus))
            .fetchJoin()
            .select(Projections.fields(DeliveryWithStatusResponseDto.class,
                delivery.deliveryNo,
                delivery.order.orderNo,
                delivery.trackingNo,
                deliveryStatus.deliveryStatusEnum.stringValue().as("deliveryStatus")))
            .where(deliveryStatus.deliveryStatusEnum.stringValue().eq(
                DeliveryStatusEnum.WAIT_DELIVERY.getDeliveryStatus()))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Delivery> findDeliveryEntityListWithStatusWait() {
        QDelivery delivery = QDelivery.delivery;
        QDeliveryStatusHistory deliveryStatusHistory = QDeliveryStatusHistory.deliveryStatusHistory;
        QDeliveryStatus deliveryStatus = QDeliveryStatus.deliveryStatus;

        return from(delivery)
            .innerJoin(deliveryStatusHistory)
            .on(delivery.deliveryNo.eq(deliveryStatusHistory.delivery.deliveryNo))
            .fetchJoin()
            .innerJoin(deliveryStatus)
            .on(deliveryStatusHistory.deliveryStatus.eq(deliveryStatus))
            .fetchJoin()
            .where(deliveryStatus.deliveryStatusEnum.stringValue().eq(
                DeliveryStatusEnum.WAIT_DELIVERY.getDeliveryStatus()))
            .fetch();
    }
}
