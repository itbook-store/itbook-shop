package shop.itbook.itbookshop.deliverygroup.delivery.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.CustomDeliveryRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;

/**
 * CustomDeliveryRepository 의 구현 클래스.
 *
 * @author 정재원
 * @since 1.0
 */
public class DeliveryRepositoryImpl extends QuerydslRepositorySupport implements
    CustomDeliveryRepository {

    /**
     * Instantiates a new Delivery repository.
     */
    public DeliveryRepositoryImpl() {
        super(Delivery.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:LocalVariableName")
    @Override
    public Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatus(Pageable pageable) {

        return getDeliveryWithStatusResponseDtoList(pageable, OrderStatusEnum.PAYMENT_COMPLETE);
    }

    private Page<DeliveryWithStatusResponseDto> getDeliveryWithStatusResponseDtoList(
        Pageable pageable, OrderStatusEnum orderStatusEnum) {
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;

        QDelivery qDelivery = QDelivery.delivery;

        JPQLQuery<DeliveryWithStatusResponseDto> jpqlQuery = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrder)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
            .innerJoin(qDelivery)
            .on(qDelivery.order.eq(qOrderStatusHistory.order))
            .where(qOrderStatusHistory.orderStatus.orderStatusEnum.eq(orderStatusEnum)
                .and(qOrderStatusHistory2.orderStatusHistoryNo.isNull()))
            .select(Projections.fields(DeliveryWithStatusResponseDto.class,
                qDelivery.deliveryNo,
                qDelivery.order.orderNo,
                qDelivery.trackingNo,
                qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("deliveryStatus")))
            .orderBy(qDelivery.deliveryNo.desc());

        List<DeliveryWithStatusResponseDto> deliveryWithStatusResponseDtoList =
            jpqlQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(deliveryWithStatusResponseDtoList, pageable,
            jpqlQuery::fetchCount);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:LocalVariableName")
    @Override
    public Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait(Pageable pageable) {
        return getDeliveryWithStatusResponseDtoList(pageable, OrderStatusEnum.WAIT_DELIVERY);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:LocalVariableName")
    @Override
    public List<Delivery> findDeliveryEntityListWithStatusWait() {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;

        QDelivery qDelivery = QDelivery.delivery;

        return from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrder)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
            .innerJoin(qDelivery)
            .on(qDelivery.order.eq(qOrderStatusHistory.order))
            .where(qOrderStatusHistory.orderStatus.orderStatusEnum.eq(OrderStatusEnum.WAIT_DELIVERY)
                .and(qOrderStatusHistory2.orderStatusHistoryNo.isNull()))
            .select(qDelivery)
            .fetch();
    }

    @Override
    public String findTrackingNoByOrderNo(Long orderNo) {

        QOrder qOrder = QOrder.order;
        QDelivery qDelivery = QDelivery.delivery;

        return from(qOrder)
            .leftJoin(qDelivery)
            .on(qOrder.eq(qDelivery.order))
            .where(qOrder.orderNo.eq(orderNo))
            .select(qDelivery.trackingNo)
            .fetchOne();
    }
}
