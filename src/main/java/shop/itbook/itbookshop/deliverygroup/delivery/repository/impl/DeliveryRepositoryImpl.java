package shop.itbook.itbookshop.deliverygroup.delivery.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.CustomDeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.QDeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.QDeliveryStatusHistory;

/**
 * CustomDeliveryRepository 의 구현 클래스.
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
    @SuppressWarnings("checkstyle:LocalVariableName")
    @Override
    public List<DeliveryWithStatusResponseDto> findDeliveryListWithStatus() {
        QDelivery qDelivery = QDelivery.delivery;

        QDeliveryStatusHistory qDeliveryStatusHistory1 =
            QDeliveryStatusHistory.deliveryStatusHistory;

        QDeliveryStatusHistory qDeliveryStatusHistory2 =
            new QDeliveryStatusHistory("deliveryStatusHistory2");

        QDeliveryStatus qDeliveryStatus = QDeliveryStatus.deliveryStatus;

//        from(delivery)
//            .innerJoin(qDeliveryStatusHistory1)
//            .on(delivery.deliveryNo.eq(qDeliveryStatusHistory1.delivery.deliveryNo))
//            .fetchJoin()
//            .innerJoin(qDeliveryStatus)
//            .on(qDeliveryStatusHistory1.deliveryStatus.eq(qDeliveryStatus))
//            .fetchJoin()
//            .orderBy(qDeliveryStatusHistory1.deliveryStatusHistoryNo.desc())
//            .select(Projections.fields(DeliveryWithStatusResponseDto.class,
//                delivery.deliveryNo,
//                delivery.order.orderNo,
//                delivery.trackingNo,
//                qDeliveryStatus.deliveryStatusEnum.stringValue().as("deliveryStatus")))
//            .fetch()

        return getDeliveryStatusHistoryJPQLQuery(qDeliveryStatusHistory1, qDeliveryStatusHistory2,
            qDelivery,
            qDeliveryStatus, qDeliveryStatusHistory2.deliveryStatusHistoryNo.isNull())
            .select(Projections.fields(DeliveryWithStatusResponseDto.class,
                qDelivery.deliveryNo,
                qDelivery.order.orderNo,
                qDelivery.trackingNo,
                qDeliveryStatus.deliveryStatusEnum.stringValue().as("deliveryStatus")))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:LocalVariableName")
    @Override
    public List<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait() {
        QDelivery qDelivery = QDelivery.delivery;

        QDeliveryStatusHistory qDeliveryStatusHistory1 =
            QDeliveryStatusHistory.deliveryStatusHistory;

        QDeliveryStatusHistory qDeliveryStatusHistory2 =
            new QDeliveryStatusHistory("deliveryStatusHistory2");

        QDeliveryStatus qDeliveryStatus = QDeliveryStatus.deliveryStatus;

        return getDeliveryStatusHistoryJPQLQuery(qDeliveryStatusHistory1, qDeliveryStatusHistory2,
            qDelivery,
            qDeliveryStatus, qDeliveryStatusHistory2.deliveryStatusHistoryNo.isNull()
                .and(qDeliveryStatus.deliveryStatusEnum.eq(DeliveryStatusEnum.WAIT_DELIVERY)))
            .select(Projections.fields(DeliveryWithStatusResponseDto.class,
                qDelivery.deliveryNo,
                qDelivery.order.orderNo,
                qDelivery.trackingNo,
                qDeliveryStatus.deliveryStatusEnum.stringValue().as("deliveryStatus")))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:LocalVariableName")
    @Override
    public List<Delivery> findDeliveryEntityListWithStatusWait() {
        QDelivery qDelivery = QDelivery.delivery;

        QDeliveryStatusHistory qDeliveryStatusHistory1 =
            QDeliveryStatusHistory.deliveryStatusHistory;

        QDeliveryStatusHistory qDeliveryStatusHistory2 =
            new QDeliveryStatusHistory("deliveryStatusHistory2");

        QDeliveryStatus qDeliveryStatus = QDeliveryStatus.deliveryStatus;

        return getDeliveryStatusHistoryJPQLQuery(qDeliveryStatusHistory1, qDeliveryStatusHistory2,
            qDelivery,
            qDeliveryStatus, qDeliveryStatusHistory2.deliveryStatusHistoryNo.isNull()
                .and(qDeliveryStatus.deliveryStatusEnum.eq(DeliveryStatusEnum.WAIT_DELIVERY)))
            .select(qDelivery)
            .fetch();
    }

    @SuppressWarnings("checkstyle:ParameterName")
    private JPQLQuery<DeliveryStatusHistory> getDeliveryStatusHistoryJPQLQuery(
        QDeliveryStatusHistory qDeliveryStatusHistory1,
        QDeliveryStatusHistory qDeliveryStatusHistory2,
        QDelivery qDelivery, QDeliveryStatus qDeliveryStatus,
        BooleanExpression deliveryStatusHistoryNo) {
        return from(qDeliveryStatusHistory1)
            .leftJoin(qDeliveryStatusHistory2)
            .on(qDeliveryStatusHistory1.delivery.deliveryNo.eq(
                qDeliveryStatusHistory2.delivery.deliveryNo).and(
                qDeliveryStatusHistory1.deliveryStatusHistoryNo.lt(
                    qDeliveryStatusHistory2.deliveryStatusHistoryNo)))
            .innerJoin(qDelivery)
            .on(qDelivery.deliveryNo.eq(qDeliveryStatusHistory1.delivery.deliveryNo))
            .fetchJoin()
            .innerJoin(qDeliveryStatus)
            .on(qDeliveryStatusHistory1.deliveryStatus.eq(qDeliveryStatus))
            .fetchJoin()
            .where(deliveryStatusHistoryNo);
    }
}
