package shop.itbook.itbookshop.ordergroup.order.repository.impl;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.QCategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.QCouponIssue;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.QOrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.QProductCouponApply;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.order.exception.InvalidOrderCodeException;
import shop.itbook.itbookshop.ordergroup.order.exception.OrderNotFoundException;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.CustomOrderRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.QOrderNonMember;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.QOrderSubscription;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * CustomOrderRepository ?????????????????? ????????? ???????????????.
 *
 * @author ?????????
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
                qOrderStatusHistory.order.orderCreatedAt,
                qOrderStatusHistory.order.recipientName,
                qOrderStatusHistory.order.recipientPhoneNumber,
                qDelivery.trackingNo
            ))
            .orderBy(qOrderStatusHistory.order.orderNo.desc());

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
            .on(qProduct.eq(qOrderProduct.product).and(qProduct.isSubscription.isFalse()))
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
            .orderBy(qOrderStatusHistory.order.orderNo.desc());

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
    public Page<OrderSubscriptionAdminListDto> findAllSubscriptionOrderListOfAdmin(
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
                .leftJoin(qMember)
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
    public Page<OrderSubscriptionListDto> findAllSubscriptionOrderListOfMember(Pageable pageable,
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

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDetailsResponseDto findOrderDetail(Long orderNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;
        QDelivery qDelivery = QDelivery.delivery;
        QOrderTotalCouponApply qOrderTotalCouponApply =
            QOrderTotalCouponApply.orderTotalCouponApply;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QCoupon qCoupon = QCoupon.coupon;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProductCouponApply qProductCouponApply = QProductCouponApply.productCouponApply;
        QCategoryCouponApply qCategoryCouponApply = QCategoryCouponApply.categoryCouponApply;
        QOrderNonMember qOrderNonMember = QOrderNonMember.orderNonMember;

        JPQLQuery<OrderStatusHistory> jpqlQuery =
            getJpqlQuery(orderNo, qOrderStatusHistory, qOrderStatusHistory2, qOrder);

        OrderDetailsResponseDto orderDetailsResponseDto = jpqlQuery
            .leftJoin(qDelivery)
            .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
            .leftJoin(qOrderTotalCouponApply)
            .on(qOrder.orderNo.eq(qOrderTotalCouponApply.order.orderNo))
            .leftJoin(qCouponIssue)
            .on(qCouponIssue.couponIssueNo.eq(qOrderTotalCouponApply.couponIssueNo))
            .leftJoin(qCoupon)
            .on(qCoupon.couponNo.eq(qCouponIssue.coupon.couponNo))
            .leftJoin(qOrderNonMember)
            .on(qOrderNonMember.order.eq(qOrder))
            .select(Projections.fields(OrderDetailsResponseDto.class,
                    qOrder.orderNo,
                    qOrderStatusHistory.orderStatusCreatedAt,
                    qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                    qOrder.orderCreatedAt,
                    qOrder.amount,
                    qOrder.deliveryFee,
                    qDelivery.deliveryNo,
                    qDelivery.trackingNo,
                    qCoupon.name.as("couponName"),
                    qCoupon.amount.as("totalCouponAmount"),
                    qCoupon.percent.as("totalCouponPercent"),
                    qOrderNonMember.nonMemberOrderCode,
                    Projections.fields(OrderDestinationDto.class,
                        qOrder.recipientName.as("recipientName"),
                        qOrder.recipientPhoneNumber.as("recipientPhoneNumber"),
                        qOrder.postcode.as("postcode"),
                        qOrder.roadNameAddress.as("roadNameAddress"),
                        qOrder.recipientAddressDetails.as("recipientAddressDetails")
                    ).as("orderDestinationDto")
                )
            ).fetchOne();

        List<OrderProductDetailResponseDto> productDetailList =
            getJpqlQuery(orderNo, qOrderStatusHistory, qOrderStatusHistory2, qOrder)
                .innerJoin(qOrderProduct)
                .on(qOrderProduct.order.orderNo.eq(qOrder.orderNo))
                .leftJoin(qProductCouponApply)
                .on(qOrderProduct.orderProductNo.eq(
                    qProductCouponApply.orderProduct.orderProductNo))
                .leftJoin(qCategoryCouponApply)
                .on(qOrderProduct.orderProductNo.eq(
                    qCategoryCouponApply.orderProduct.orderProductNo))
                .leftJoin(qCouponIssue)
                .on(qCouponIssue.couponIssueNo.eq(qProductCouponApply.couponIssueNo)
                    .or(qCouponIssue.couponIssueNo.eq(qCategoryCouponApply.couponIssueNo)))
                .leftJoin(qCoupon)
                .on(qCoupon.couponNo.eq(qCouponIssue.coupon.couponNo))
                .select(Projections.fields(OrderProductDetailResponseDto.class,
                    qOrderProduct.orderProductNo,
                    qOrderProduct.product.productNo,
                    qOrderProduct.product.name.as("productName"),
                    qOrderProduct.count,
                    qOrderProduct.productPrice,
                    qOrderProduct.product.thumbnailUrl.as("fileThumbnailsUrl"),
                    qCoupon.name.as("couponName"),
                    qCoupon.amount.as("couponAmount"),
                    qCoupon.percent.as("couponPercent"),
                    qOrderProduct.product.fixedPrice,
                    qOrderProduct.product.discountPercent
                ))
                .fetch();

        orderDetailsResponseDto.setOrderProductDetailResponseDtoList(productDetailList);

        if (Objects.nonNull(orderDetailsResponseDto.getTotalCouponPercent())) {
            long orderProductCouponAppliedAmount =
                orderDetailsResponseDto.getOrderProductDetailResponseDtoList().stream().mapToLong(
                    OrderProductDetailResponseDto::getProductPrice
                ).sum();

            long totalCouponPercentAmount = (long) (orderProductCouponAppliedAmount
                * (orderDetailsResponseDto.getTotalCouponPercent() * 0.01));

            orderDetailsResponseDto.setCouponAmount(totalCouponPercentAmount);
        }
        return orderDetailsResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> findOrderOfLatestStatus(Long orderNo) {
        QOrder qOrder = QOrder.order;
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");

        return Optional.of(from(qOrder)
            .innerJoin(qOrderStatusHistory)
            .on(qOrder.orderNo.eq(qOrderStatusHistory.order.orderNo))
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.orderStatusHistoryNo.eq(
                    qOrderStatusHistory2.orderStatusHistoryNo)
                .and(qOrderStatusHistory.order.orderNo.lt(qOrderStatusHistory2.order.orderNo)))
            .where(qOrder.orderNo.eq(orderNo))
            .orderBy(qOrderStatusHistory.orderStatusHistoryNo.desc())
            .fetchFirst());

    }

    private JPQLQuery<OrderStatusHistory> getJpqlQuery(Long orderNo,
                                                       QOrderStatusHistory qOrderStatusHistory,
                                                       QOrderStatusHistory qOrderStatusHistory2,
                                                       QOrder qOrder) {
        return from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrder)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
            .where(qOrder.orderNo.eq(orderNo)
                .and(qOrderStatusHistory2.orderStatusHistoryNo.isNull()));
    }

    @Override
    public List<OrderSubscriptionDetailsResponseDto> findOrderSubscriptionDetailsResponseDto(
        Long orderNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;

        QDelivery qDelivery = QDelivery.delivery;

        // ?????? ?????? ??????
        QOrderTotalCouponApply qOrderTotalCouponApply =
            QOrderTotalCouponApply.orderTotalCouponApply;
        QCouponIssue qCouponIssueTotal = QCouponIssue.couponIssue;
        QCoupon qCouponTotal = QCoupon.coupon;

        // ?????? ?????? ??????
        QProductCouponApply qProductCouponApply = QProductCouponApply.productCouponApply;
        QCategoryCouponApply qCategoryCouponApply = QCategoryCouponApply.categoryCouponApply;
        QCouponIssue qCouponIssueNotTotal = new QCouponIssue("couponIssue2");
        QCoupon qCouponNotTotal = new QCoupon("coupon2");

        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        Integer subscriptionPeriod = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)
                ))
            .innerJoin(qOrderSubscription)
            .on(qOrderSubscription.orderNo.eq(orderNo))
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull()
                .and(qOrderStatusHistory.order.orderNo.eq(orderNo)))
            .select(qOrderSubscription.subscriptionPeriod)
            .fetchOne();

        return from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.eq(qOrderStatusHistory2.order)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)
                )
            )
            .innerJoin(qOrder)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
            .leftJoin(qDelivery)
            .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
            .innerJoin(qOrderProduct)
            .on(qOrder.orderNo.eq(qOrderProduct.order.orderNo))
            .innerJoin(qOrderSubscription)
            .on(qOrderSubscription.orderNo.eq(qOrder.orderNo))

            // ?????? ?????? ??????
            .leftJoin(qOrderTotalCouponApply)
            .on(qOrder.eq(qOrderTotalCouponApply.order))
            .leftJoin(qCouponIssueTotal)
            .on(qCouponIssueTotal.couponIssueNo.eq(qOrderTotalCouponApply.couponIssueNo))
            .leftJoin(qCouponTotal)
            .on(qCouponTotal.couponNo.eq(qCouponIssueTotal.coupon.couponNo))

            // ?????? ?????? ?????? : ?????? ??????, ????????????
            .leftJoin(qProductCouponApply)
            .on(qProductCouponApply.orderProduct.eq(qOrderProduct))
            .leftJoin(qCategoryCouponApply)
            .on(qCategoryCouponApply.orderProduct.eq(qOrderProduct))
            .leftJoin(qCouponIssueNotTotal)
            .on(qCouponIssueNotTotal.couponIssueNo.eq(qProductCouponApply.couponIssueNo)
                .or(qCouponIssueNotTotal.couponIssueNo.eq(qCategoryCouponApply.couponIssueNo)))
            .leftJoin(qCouponNotTotal)
            .on(qCouponNotTotal.couponNo.eq(qCouponIssueNotTotal.coupon.couponNo))
            .where(
                qOrder.orderNo.between(orderNo, orderNo + subscriptionPeriod - 1)
                    .and(qOrderStatusHistory2.orderStatusHistoryNo.isNull()))
            .select(Projections.fields(OrderSubscriptionDetailsResponseDto.class,
                Projections.fields(OrderDestinationDto.class,
                    qOrder.recipientName,
                    qOrder.recipientPhoneNumber,
                    qOrder.postcode,
                    qOrder.roadNameAddress,
                    qOrder.recipientAddressDetails
                ).as("orderDestinationDto"),
                qOrder.orderNo,
                qOrderStatusHistory.orderStatusCreatedAt,
                qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                qOrder.orderCreatedAt,
                qOrder.amount,
                qOrder.deliveryFee,
                qDelivery.deliveryNo,
                qDelivery.trackingNo,
                qOrder.selectedDeliveryDate,
                // ?????? ?????? ??????
                qCouponTotal.name.as("totalCouponName"),
                qCouponTotal.amount.as("totalCouponAmount"),
                qCouponTotal.percent.as("totalCouponPercent"),
                // orderProduct
                qOrderProduct.orderProductNo,
                qOrderProduct.product.productNo,
                qOrderProduct.product.name.as("productName"),
                qOrderProduct.product.fixedPrice,
                qOrderProduct.product.discountPercent,
                qOrderProduct.count,
                qOrderProduct.productPrice,
                qOrderProduct.product.thumbnailUrl.as("fileThumbnailsUrl"),
                // ?????? ?????? ??????
                qCouponNotTotal.name.as("couponName"),
                qCouponNotTotal.amount.as("couponAmount"),
                qCouponNotTotal.percent.as("couponPercent")
            )).fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDetailsResponseDto findOrderDetailOfNonMember(Long orderNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;
        QDelivery qDelivery = QDelivery.delivery;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        QOrderNonMember qOrderNonMember = QOrderNonMember.orderNonMember;

        checkValidOrderNo(orderNo, qOrder);

        JPQLQuery<OrderStatusHistory> jpqlQuery =
            getJpqlQuery(orderNo, qOrderStatusHistory, qOrderStatusHistory2, qOrder);
        try {

            OrderDetailsResponseDto orderDetailsResponseDto = jpqlQuery
                .leftJoin(qDelivery)
                .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
                .innerJoin(qOrderNonMember)
                .on(qOrderNonMember.order.eq(qOrder))
                .select(Projections.fields(OrderDetailsResponseDto.class,
                        qOrder.orderNo,
                        qOrderStatusHistory.orderStatusCreatedAt,
                        qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                        qOrder.orderCreatedAt,
                        qOrder.amount,
                        qOrder.deliveryFee,
                        qDelivery.deliveryNo,
                        qDelivery.trackingNo,
                        qOrderNonMember.nonMemberOrderCode,
                        Projections.fields(OrderDestinationDto.class,
                            qOrder.recipientName.as("recipientName"),
                            qOrder.recipientPhoneNumber.as("recipientPhoneNumber"),
                            qOrder.postcode.as("postcode"),
                            qOrder.roadNameAddress.as("roadNameAddress"),
                            qOrder.recipientAddressDetails.as("recipientAddressDetails")
                        ).as("orderDestinationDto")
                    )
                ).fetchOne();

            List<OrderProductDetailResponseDto> productDetailList =
                getJpqlQuery(orderNo, qOrderStatusHistory, qOrderStatusHistory2, qOrder)
                    .innerJoin(qOrderProduct)
                    .on(qOrderProduct.order.orderNo.eq(qOrder.orderNo))
                    .select(Projections.fields(OrderProductDetailResponseDto.class,
                        qOrderProduct.orderProductNo,
                        qOrderProduct.product.name.as("productName"),
                        qOrderProduct.count,
                        qOrderProduct.productPrice,
                        qOrderProduct.product.thumbnailUrl.as("fileThumbnailsUrl"),
                        qOrderProduct.product.fixedPrice,
                        qOrderProduct.product.discountPercent
                    ))
                    .fetch();

            orderDetailsResponseDto.setOrderProductDetailResponseDtoList(productDetailList);


            return orderDetailsResponseDto;
        } catch (Exception e) {
            throw new InvalidOrderCodeException();
        }

    }

    @Override
    public List<OrderSubscriptionDetailsResponseDto> findOrderSubscriptionDetailsOfNonMember(
        Long orderNo) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatusHistory qOrderStatusHistory2 = new QOrderStatusHistory("qOrderStatusHistory2");
        QOrder qOrder = QOrder.order;

        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;

        QDelivery qDelivery = QDelivery.delivery;

        QOrderNonMember qOrderNonMember = QOrderNonMember.orderNonMember;

        checkValidOrderNo(orderNo, qOrder);

        Integer subscriptionPeriod = from(qOrderStatusHistory)
            .leftJoin(qOrderStatusHistory2)
            .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                .and(qOrderStatusHistory.orderStatusHistoryNo
                    .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
            .innerJoin(qOrderSubscription)
            .on(qOrderSubscription.orderNo.eq(orderNo))
            .where(qOrderStatusHistory2.orderStatusHistoryNo.isNull()
                .and(qOrderStatusHistory.order.orderNo.eq(orderNo)))
            .select(qOrderSubscription.subscriptionPeriod)
            .fetchOne();

        try {
            List<OrderSubscriptionDetailsResponseDto> fetch = from(qOrderStatusHistory)
                .leftJoin(qOrderStatusHistory2)
                .on(qOrderStatusHistory.order.orderNo.eq(qOrderStatusHistory2.order.orderNo)
                    .and(qOrderStatusHistory.orderStatusHistoryNo
                        .lt(qOrderStatusHistory2.orderStatusHistoryNo)))
                .innerJoin(qOrder)
                .on(qOrderStatusHistory.order.orderNo.eq(qOrder.orderNo))
                .leftJoin(qDelivery)
                .on(qOrder.orderNo.eq(qDelivery.order.orderNo))
                .innerJoin(qOrderSubscription)
                .on(qOrderSubscription.orderNo.eq(qOrder.orderNo))
                .leftJoin(qOrderNonMember)
                .on(qOrderNonMember.order.eq(qOrder))
                .innerJoin(qOrderProduct)
                .on(qOrderProduct.order.eq(qOrder))
                .where(
                    qOrder.orderNo.between(orderNo, orderNo + subscriptionPeriod - 1)
                        .and(qOrderStatusHistory2.orderStatusHistoryNo.isNull()))
                .select(Projections.fields(OrderSubscriptionDetailsResponseDto.class,
                    Projections.fields(OrderDestinationDto.class,
                        qOrder.recipientName,
                        qOrder.recipientPhoneNumber,
                        qOrder.postcode,
                        qOrder.roadNameAddress,
                        qOrder.recipientAddressDetails
                    ).as("orderDestinationDto"),
                    qOrder.orderNo,
                    qOrderStatusHistory.orderStatusCreatedAt,
                    qOrderStatusHistory.orderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                    qOrder.orderCreatedAt,
                    qOrder.amount,
                    qOrder.deliveryFee,
                    qDelivery.deliveryNo,
                    qDelivery.trackingNo,
                    qOrder.selectedDeliveryDate,
                    qOrderNonMember.nonMemberOrderCode,

                    qOrderProduct.orderProductNo,
                    qOrderProduct.product.name.as("productName"),
                    qOrderProduct.count,
                    qOrderProduct.productPrice,
                    qOrderProduct.product.thumbnailUrl.as("fileThumbnailsUrl")
                )).fetch();

            return fetch;
        } catch (Exception e) {
            throw new InvalidOrderException();
        }
    }

    private void checkValidOrderNo(Long orderNo, QOrder qOrder) {
        if (Objects.isNull(from(qOrder)
            .where(qOrder.orderNo.eq(orderNo))
            .fetch())) {
            throw new OrderNotFoundException();
        }
    }
}
