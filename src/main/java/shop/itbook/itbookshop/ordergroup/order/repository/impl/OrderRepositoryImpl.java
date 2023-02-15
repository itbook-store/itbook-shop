package shop.itbook.itbookshop.ordergroup.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.CustomOrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;

/**
 * CustomOrderRepository 인터페이스의 기능을 구현합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements
    CustomOrderRepository {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public Page<OrderListMemberViewResponseDto> getOrderListOfMemberWithStatus(Pageable pageable,
                                                                               Long memberNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");

        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        QOrderMember qOrderMember = QOrderMember.orderMember;

        JPQLQuery<OrderListMemberViewResponseDto> jpqlQuery = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(
                qOrderStatusHistory2.order.orderNo).and(
                qOrderStatusHistory.orderStatusHistoryNo.lt(
                    qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
            .innerJoin(qOrderMember)
            .on(qOrderMember.order.eq(qOrderStatusHistory.order)
                .and(qOrderMember.member.memberNo.eq(memberNo)))
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull())
            .select(Projections.fields(OrderListMemberViewResponseDto.class,
                qOrderStatusHistory.order.orderNo,
                qOrderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                qOrderStatusHistory.order.recipientName,
                qOrderStatusHistory.order.recipientPhoneNumber
            ))
            .orderBy(qOrderStatusHistory.order.orderNo.asc());

        QDelivery qDelivery = QDelivery.delivery;

        List<OrderListMemberViewResponseDto> orderListViewResponseDtoList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Delivery> deliveryList = from(qDelivery)
            .innerJoin(qOrderMember)
            .on(qOrderMember.order.eq(qDelivery.order)
                .and(qOrderMember.member.memberNo.eq(memberNo)))
            .select(qDelivery)
            .orderBy(qDelivery.order.orderNo.asc())
            .fetch();

        Queue<Delivery> deliveryQueue = new LinkedList<>(deliveryList);

        if (!deliveryQueue.isEmpty()) {
            orderListViewResponseDtoList.forEach(
                orderListMemberViewResponseDto -> {
                    if (Objects.equals(orderListMemberViewResponseDto.getOrderNo(),
                        deliveryQueue.peek().getDeliveryNo())) {
                        orderListMemberViewResponseDto.setTrackingNo(
                            Objects.requireNonNull(deliveryQueue.poll()).getTrackingNo());
                    }
                }
            );
        }

        return PageableExecutionUtils.getPage(orderListViewResponseDtoList, pageable,
            jpqlQuery::fetchCount);
    }

    @Override
    public String findOrderStatusByOrderNo(Long orderNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");

        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        return from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(
                qOrderStatusHistory2.order.orderNo).and(
                qOrderStatusHistory.orderStatusHistoryNo.lt(
                    qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull()
                .and(qOrderStatusHistory.order.orderNo.eq(orderNo)))
            .select(qOrderStatus.orderStatusEnum.stringValue())
            .fetchOne();
    }

    @Override
    public List<OrderDestinationDto> findOrderDestinationsByOrderNo(Long orderNo) {

        QOrder qOrder = QOrder.order;

        return from(qOrder)
            .where(qOrder.orderNo.eq(orderNo))
            .select(Projections.fields(OrderDestinationDto.class,
                qOrder.recipientName, qOrder.recipientPhoneNumber,
                qOrder.postcode, qOrder.roadNameAddress, qOrder.recipientAddressDetails
            ))
            .fetch();
    }

    @Override
    public Page<OrderListAdminViewResponseDto> getOrderListOfAdminWithStatus(Pageable pageable) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");

        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        JPQLQuery<OrderListAdminViewResponseDto> jpqlQuery = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(
                qOrderStatusHistory2.order.orderNo).and(
                qOrderStatusHistory.orderStatusHistoryNo.lt(
                    qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull())
            .select(Projections.fields(OrderListAdminViewResponseDto.class,
                qOrderStatusHistory.order.orderNo,
                qOrderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                qOrderStatusHistory.order.recipientName,
                qOrderStatusHistory.order.recipientPhoneNumber
            ))
            .orderBy(qOrderStatusHistory.order.orderNo.asc());

        QDelivery qDelivery = QDelivery.delivery;

        List<OrderListAdminViewResponseDto> orderListAdminViewResponseDtoList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Delivery> deliveryList = from(qDelivery)
            .select(qDelivery)
            .orderBy(qDelivery.order.orderNo.asc())
            .fetch();

        Queue<Delivery> deliveryQueue = new LinkedList<>(deliveryList);

        if (!deliveryQueue.isEmpty()) {
            
            orderListAdminViewResponseDtoList.forEach(
                orderListAdminViewResponseDto -> {
                    if (Objects.equals(orderListAdminViewResponseDto.getOrderNo(),
                        deliveryQueue.peek().getDeliveryNo())) {
                        orderListAdminViewResponseDto.setTrackingNo(
                            Objects.requireNonNull(deliveryQueue.poll()).getTrackingNo());
                    }
                }
            );
        }

        return PageableExecutionUtils.getPage(orderListAdminViewResponseDtoList, pageable,
            jpqlQuery::fetchCount);
    }
}
