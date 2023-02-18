package shop.itbook.itbookshop.ordergroup.order.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.QCategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.QCouponIssue;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.QOrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.QProductCouponApply;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderTotalResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.CustomOrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
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

        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        QProduct qProduct = QProduct.product;
        QDelivery qDelivery = QDelivery.delivery;

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
            .innerJoin(qOrderProduct)
            .on(qOrderProduct.order.eq(qOrderStatusHistory.order))
            .distinct()
            .innerJoin(qProduct)
            .on(qProduct.eq(qOrderProduct.product).and(qProduct.isSubscription.isFalse()))
            .leftJoin(qDelivery)
            .on(qDelivery.order.eq(qOrderStatusHistory.order))
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull())
            .select(Projections.fields(OrderListMemberViewResponseDto.class,
                qOrderStatusHistory.order.orderNo,
                qOrderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                qOrderStatusHistory.order.recipientName,
                qOrderStatusHistory.order.recipientPhoneNumber,
                qDelivery.trackingNo
            ))
            .orderBy(qOrderStatusHistory.order.orderNo.asc());

        List<OrderListMemberViewResponseDto> orderListViewResponseDtoList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

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
    public OrderDestinationDto findOrderDestinationsByOrderNo(Long orderNo) {

        QOrder qOrder = QOrder.order;

        return from(qOrder)
            .where(qOrder.orderNo.eq(orderNo))
            .select(Projections.fields(OrderDestinationDto.class,
                qOrder.recipientName, qOrder.recipientPhoneNumber,
                qOrder.postcode, qOrder.roadNameAddress, qOrder.recipientAddressDetails
            ))
            .fetchOne();
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

    @Override
    public Page<OrderSubscriptionAdminListDto> findAllSubscriptionOrderListByAdmin(
        Pageable pageable) {
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;
        QDelivery qDelivery = QDelivery.delivery;
        QOrderMember qOrderMember = QOrderMember.orderMember;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QMember qMember = QMember.member;
        QProduct qProduct = QProduct.product;

        JPQLQuery<OrderSubscriptionAdminListDto> jpqlQuery =
            from(qOrderStatusHistory)
                .leftJoin(qOrderStatusHistory2)
                .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                    .and(qOrderStatusHistory.orderStatusHistoryNo
                        .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
                .innerJoin(qOrder)
                .on(qOrderStatusHistory.order.eq(qOrder))
                .innerJoin(qOrderSubscription)
                .on(qOrder.orderNo.eq(qOrderSubscription.orderNo))
                .leftJoin(qDelivery)
                .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
                .leftJoin(qOrderMember)
                .on(qOrder.orderNo.eq(qOrderMember.orderNo))
                .innerJoin(qMember)
                .on(qOrderMember.member.memberNo.eq(qMember.memberNo))
                .innerJoin(qOrderProduct)
                .on(qOrder.orderNo.eq(qOrderProduct.order.orderNo))
                .innerJoin(qProduct)
                .on(qOrderProduct.product.productNo.eq(qProduct.productNo))
                .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull()
                    .and(qOrderSubscription.sequence.eq(1)))
                .select(Projections.fields(OrderSubscriptionAdminListDto.class,
                        qOrderStatusHistory.order.orderNo,
                        qMember.memberId,
                        qProduct.name.as("productName"),
                        qOrderStatusHistory.order.orderCreatedAt,
                        qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                        qOrderStatusHistory.order.recipientName,
                        qDelivery.trackingNo,
                        qOrderSubscription.subscriptionPeriod
                    )
                )
                .orderBy(qOrder.orderNo.desc());

        List<OrderSubscriptionAdminListDto> orderSubscriptionListDtoList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(orderSubscriptionListDtoList, pageable,
            jpqlQuery::fetchCount);
    }

    @Override
    public Page<OrderSubscriptionListDto> findAllSubscriptionOrderListByMember(Pageable pageable,
                                                                               Long memberNo) {
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;
        QDelivery qDelivery = QDelivery.delivery;
        QOrderMember qOrderMember = QOrderMember.orderMember;

        JPQLQuery<OrderSubscriptionListDto> jpqlQuery =
            from(qOrderStatusHistory)
                .leftJoin(qOrderStatusHistory2)
                .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                    .and(qOrderStatusHistory.orderStatusHistoryNo
                        .lt(qOrderStatusHistory2.orderStatusHistoryNo))
                )
                .innerJoin(qOrder)
                .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
                .innerJoin(qOrderSubscription)
                .on(qOrder.orderNo.eq(qOrderSubscription.orderNo))
                .leftJoin(qDelivery)
                .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
                .innerJoin(qOrderMember)
                .on(qOrder.orderNo.eq(qOrderMember.orderNo)
                    .and(qOrderMember.member.memberNo.eq(memberNo)))
                .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull()
                    .and(qOrderSubscription.sequence.eq(1)))
                .select(Projections.fields(OrderSubscriptionListDto.class,
                        qOrder.orderNo,
                        qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                        qOrder.recipientName,
                        qOrder.recipientPhoneNumber,
                        qDelivery.trackingNo,
                        qOrderSubscription.subscriptionPeriod
                    )
                ).orderBy(qOrder.orderNo.desc());

        List<OrderSubscriptionListDto> orderSubscriptionListDtoList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(orderSubscriptionListDtoList, pageable,
            jpqlQuery::fetchCount);

    }

    @Override
    public Order findOrderByDeliveryNo(Long deliveryNo) {

        QDelivery qDelivery = QDelivery.delivery;
        QOrder qOrder = QOrder.order;

        return from(qOrder)
            .innerJoin(qDelivery)
            .on(qDelivery.order.eq(qOrder).and(qDelivery.deliveryNo.eq(deliveryNo)))
            .fetchOne();
    }

    @Override
    public OrderTotalResponseDto findOrderDetail(Long orderNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;
        QDelivery qDelivery = QDelivery.delivery;
        QOrderTotalCouponApply qOrderTotalCouponApply =
            QOrderTotalCouponApply.orderTotalCouponApply;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QCoupon qCoupon = QCoupon.coupon;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;
        QProductCouponApply qProductCouponApply = QProductCouponApply.productCouponApply;
        QCategoryCouponApply qCategoryCouponApply = QCategoryCouponApply.categoryCouponApply;

        JPQLQuery<OrderStatusHistory> jpqlQuery = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)
                )
            )
            .innerJoin(qOrder)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
            .leftJoin(qDelivery)
            .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
            .leftJoin(qOrderTotalCouponApply)
            .on(qOrder.orderNo.eq(qOrderTotalCouponApply.order.orderNo))
            .innerJoin(qOrderProduct)
            .on(qOrder.orderNo.eq(qOrderProduct.order.orderNo))
            .where(
                qOrder.orderNo.eq(orderNo)
                    .and(qOrderStatusHistory2.orderStatusHistoryNo.isNull())
            );

        OrderTotalResponseDto orderTotalResponseDto = jpqlQuery
            .select(Projections.fields(OrderTotalResponseDto.class,
                    qOrder.orderNo,
                    qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                    qOrder.orderCreatedAt,
                    qOrder.amount,
                    qOrder.deliveryFee,
                    qDelivery.deliveryNo,
                    qDelivery.trackingNo,
                    qOrderTotalCouponApply.couponIssue.coupon.name.as("couponName"),
                    qOrderTotalCouponApply.couponIssue.coupon.amount.as("totalCouponAmount"),
                    qOrderTotalCouponApply.couponIssue.coupon.percent.as("totalCouponPercent"),
                    Projections.fields(OrderDestinationDto.class,
                        qOrder.recipientName,
                        qOrder.recipientPhoneNumber,
                        qOrder.postcode,
                        qOrder.roadNameAddress,
                        qOrder.recipientAddressDetails
                    )
                )
            ).fetchOne();

//        List<OrderProductDetailResponseDto> productDetailList =
//            jpqlQuery.leftJoin(qProductCouponApply)
//                .on(qOrderProduct.orderProductNo.eq(
//                    qProductCouponApply.orderProduct.orderProductNo))
//                .leftJoin(qCategoryCouponApply)
//                .on(qOrderProduct.orderProductNo.eq(
//                    qCategoryCouponApply.orderProduct.orderProductNo))
//                .leftJoin(qCouponIssue)
//                .on(qCategoryCouponApply.couponIssue.couponIssueNo.eq(qCouponIssue.couponIssueNo))
//                .leftJoin(qCoupon)
//                .on(qCouponIssue.coupon.couponNo.eq(qCoupon.couponNo))
//                .select(Projections.fields(OrderProductDetailResponseDto.class,
//                    qOrderProduct.orderProductNo,
//                    qOrderProduct.product.name.as("productName"),
//                    qOrderProduct.count,
//                    qOrderProduct.productPrice,
//                    qOrderProduct.product.thumbnailUrl.as("fileThumbnailsUrl"),
//                    qCoupon.name.as("couponName"),
//                    qCoupon.amount.as("couponAmount"),
//                    qCoupon.percent.as("couponPercent")
//                ))
//                .fetch();
//
//        orderDetailsResponseDto.setOrderProductDetailResponseDtoList(productDetailList);


        return orderTotalResponseDto;


//                Projections.fields(OrderDetailsResponseDto.class,
//
//                    Projections.list(OrderProductDetailResponseDto.class,
//                        qOrderProduct.orderProductNo,
//                        qOrderProduct.product.name.as("productName"),
//                        qOrderProduct.count,
//                        qOrderProduct.productPrice,
//                        qOrderProduct.product.thumbnailUrl.as("fileThumbnailsUrl"),
//                        qCoupon.name.as("couponName"),
//                        qCoupon.amount.as("couponAmount"),
//                        qCoupon.percent.as("couponPercent")
//                    ),
//                    Projections.fields(OrderDestinationDto.class,
//                        qOrder.recipientName,
//                        qOrder.recipientPhoneNumber,
//                        qOrder.postcode,
//                        qOrder.roadNameAddress,
//                        qOrder.recipientAddressDetails
//                    ),
//                    qOrder.orderNo,
//                    qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue()
//                        .as("orderStatus"),
//                    qOrder.orderCreatedAt,
//                    qOrder.amount,
//                    qOrder.deliveryFee,
//                    qDelivery.deliveryNo,
//                    qDelivery.trackingNo,
//                    qOrderTotalCouponApply.couponIssue.coupon.name.as("couponName"),
//                    qOrderTotalCouponApply.couponIssue.coupon.amount.as("totalCouponAmount"),
//                    qOrderTotalCouponApply.couponIssue.coupon.percent.as("totalCouponPercent")
//                )
    }
}
