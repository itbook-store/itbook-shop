package shop.itbook.itbookshop.coupongroup.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CustomCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;

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
        QCouponType qCouponType = QCouponType.couponType;

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
                qCoupon.point,
                qCouponType.couponTypeEnum.stringValue().as("couponType")))
            .join(qCoupon.couponType, qCouponType)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return PageableExecutionUtils.getPage(couponList, pageable, jpqlQuery::fetchOne);
    }

    @Override
    public List<Coupon> findByAvailableCouponByCouponType(CouponTypeEnum couponTypeEnum) {

        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;


        JPQLQuery<Coupon> jpqlQuery = from(qCoupon)
            .select(qCoupon)
            .join(qCoupon.couponType, qCouponType)
            .where(qCoupon.couponType.couponTypeEnum.eq(couponTypeEnum))
            .where(qCoupon.couponCreatedAt.before(LocalDateTime.now()))
            .where(qCoupon.couponExpiredAt.after(LocalDateTime.now()));
        return jpqlQuery.fetch();
    }

    /**
     * 쿠폰 종류별로 페이지네이션 하여 가져오는 메소드입니다.
     *
     * @param pageable
     * @param couponTypeEnum
     * @return
     */
    @Override
    public Page<CouponListResponseDto> findByCouponAtCouponTypeList(Pageable pageable,
                                                          CouponTypeEnum couponTypeEnum) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;

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
                qCoupon.point,
                qCouponType.couponTypeEnum.stringValue().as("couponType")))
            .join(qCoupon.couponType, qCouponType)
            .where(qCouponType.couponTypeEnum.eq(couponTypeEnum))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return PageableExecutionUtils.getPage(couponList, pageable, jpqlQuery::fetchOne);
    }
}
