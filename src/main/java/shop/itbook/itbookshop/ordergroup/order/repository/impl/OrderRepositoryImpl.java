package shop.itbook.itbookshop.ordergroup.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.QDelivery;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.QOrderProductHistory;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.CustomOrderRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;

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

        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        QOrderProductHistory qOrderProductHistory1 = QOrderProductHistory.orderProductHistory;
        QOrderProductHistory qOrderProductHistory2 =
            new QOrderProductHistory("qOrderProductHistory2");

        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        QDelivery qDelivery = QDelivery.delivery;

        QOrderMember qOrderMember = QOrderMember.orderMember;

        QOrder qOrder = QOrder.order;

        JPQLQuery<OrderListMemberViewResponseDto> jpqlQuery = from(qOrderProductHistory1)
            .leftJoin(qOrderProductHistory2)
            .on(qOrderProductHistory1.orderProduct.orderProductNo.eq(
                qOrderProductHistory2.orderProduct.orderProductNo).and(
                qOrderProductHistory1.orderProductOrderStatusNo.lt(
                    qOrderProductHistory2.orderProductOrderStatusNo)))
            .innerJoin(qOrderProductHistory1.orderStatus, qOrderStatus)
            .innerJoin(qOrderProductHistory1.orderProduct, qOrderProduct)
            .innerJoin(qDelivery).on(qDelivery.order.eq(qOrderProduct.order))
            .fetchJoin()
            .innerJoin(qOrderMember).on(qOrderMember.order.eq(qOrderProduct.order)
                .and(qOrderMember.member.memberNo.eq(memberNo)))
            .fetchJoin()
            .innerJoin(qOrder).on(qOrder.eq(qOrderProduct.order))
            .where(qOrderProductHistory2.orderProductOrderStatusNo.isNull())
            .select(Projections.fields(OrderListMemberViewResponseDto.class,
                qOrderProduct.order.orderNo,
                qOrderStatus.orderStatusEnum.stringValue().as("orderStatus"),
                qOrder.recipientName,
                qOrder.recipientPhoneNumber, qDelivery.trackingNo
            ));

        List<OrderListMemberViewResponseDto> orderListViewResponseDtoListMember =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(orderListViewResponseDtoListMember, pageable,
            jpqlQuery::fetchCount);
    }
}
