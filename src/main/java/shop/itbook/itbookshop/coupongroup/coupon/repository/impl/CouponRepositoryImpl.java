package shop.itbook.itbookshop.coupongroup.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CustomCouponRepository;

/**
 * customCouponRepository 의 기능을 구현하기 위해 만든 class 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCouponRepository {
    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public Page<CouponListResponseDto> findCouponList(Pageable pageable) {
        QCoupon qCoupon = QCoupon.coupon;

        JPQLQuery<Long> jpqlQuery = from(qCoupon)
            .select(qCoupon.count());

        List<CouponListResponseDto> couponList = from(qCoupon)
            .select(Projections.fields(CouponListResponseDto.class,
                qCoupon.name,
                qCoupon.code,
                qCoupon.couponCreatedAt,
                qCoupon.couponExpiredAt,
                qCoupon.amount,
                qCoupon.percent,
                qCoupon.point))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return PageableExecutionUtils.getPage(couponList, pageable, jpqlQuery::fetchOne);
    }
}
