package shop.itbook.itbookshop.ordergroup.order.repository.impl;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.Date;
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
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.CustomOrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.QOrderSubscription;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

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

        QOrderMember qOrderMember = QOrderMember.orderMember;
        QMember qMember = QMember.member;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;
        QDelivery qDelivery = QDelivery.delivery;


        JPQLQuery<OrderListAdminViewResponseDto> jpqlQuery = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(
                qOrderStatusHistory2.order.orderNo).and(
                qOrderStatusHistory.orderStatusHistoryNo.lt(
                    qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
            .leftJoin(qOrderMember)
            .on(qOrderMember.order.eq(qOrderStatusHistory.order))
            .leftJoin(qMember)
            .on(qOrderMember.member.eq(qMember))
            .innerJoin(qOrderProduct)
            .on(qOrderProduct.order.eq(qOrderStatusHistory.order))
            .innerJoin(qProduct)
            .on(qProduct.eq(qOrderProduct.product))
            .leftJoin(qDelivery)
            .on(qDelivery.order.eq(qOrderStatusHistory.order))
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull())
            .select(Projections.fields(OrderListAdminViewResponseDto.class,
                qOrderStatusHistory.order.orderNo,
                qMember.memberId,
                qProduct.name.as("productName"),
                qOrderStatusHistory.order.orderCreatedAt,
                qOrderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                qOrderStatusHistory.order.recipientName,
                qDelivery.trackingNo
            ))
            .orderBy(qOrderStatusHistory.order.orderNo.asc());

        List<OrderListAdminViewResponseDto> orderListAdminViewResponseDtoList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(orderListAdminViewResponseDtoList, pageable,
            jpqlQuery::fetchCount);
    }

    @Override
    public List<Order> paymentCompleteSubscriptionProductStatusChangeWaitDelivery() {
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");

        QOrder qOrder = QOrder.order;
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;

        DateTemplate<Date> formattedDateOrderSelectedDeliveryDate = Expressions.dateTemplate(
            Date.class,
            "DATE_FORMAT({0}, {1})",
            qOrder.selectedDeliveryDate,
            ConstantImpl.create("%Y-%m-%d")
        );
        
        return from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrder)
            .on(qOrderStatusHistory.order.eq(qOrder))
            .innerJoin(qOrderSubscription)
            .on(qOrder.orderNo.eq(qOrderSubscription.orderNo))
            .where(qOrderStatusHistory2.isNull()
                .and(qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue()
                    .eq(OrderStatusEnum.PAYMENT_COMPLETE.getOrderStatus()))
                .and(Expressions.currentDate().eq(formattedDateOrderSelectedDeliveryDate)))
            .select(qOrder)
            .fetch();
    }
}
