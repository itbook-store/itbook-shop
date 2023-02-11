package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.QOrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.CustomOrderTotalCouponRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
public class OrderTotalCouponRepositoryImpl extends QuerydslRepositorySupport
    implements CustomOrderTotalCouponRepository {

    public OrderTotalCouponRepositoryImpl() {
        super(OrderTotalCoupon.class);
    }

    @Override
    public Page<OrderCouponListResponseDto> findTotalCouponPageList(Pageable pageable) {
        QOrderTotalCoupon qOrderTotalCoupon = QOrderTotalCoupon.orderTotalCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;

        JPQLQuery<OrderTotalCoupon> jpqlQuery = from(qOrderTotalCoupon);

        List<OrderCouponListResponseDto> totalCouponList = jpqlQuery
            .select(Projections.fields(OrderCouponListResponseDto.class,
                qCoupon.couponNo,
                qCoupon.amount, qCoupon.point, qCoupon.percent,
                qCoupon.name, qCoupon.code, qCoupon.couponCreatedAt, qCoupon.couponExpiredAt,
                qCouponType.couponTypeEnum.stringValue().as("couponType")))
            .join(qCoupon.couponType, qCouponType)
            .innerJoin(qOrderTotalCoupon.coupon, qCoupon)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(totalCouponList, pageable, jpqlQuery::fetchCount);
    }
}
