package shop.itbook.itbookshop.coupongroup.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.QCategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CustomCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.QProductCoupon;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

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

    private static final String COUPON_TYPE = "couponType";

    @Override
    public Page<AdminCouponListResponseDto> findCouponList(Pageable pageable) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCategory qCategory = QCategory.category;
        QProduct qProduct = QProduct.product;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;

        JPQLQuery<Long> jpqlQuery = from(qCoupon)
            .select(qCoupon.count());

        List<AdminCouponListResponseDto> couponList = from(qCoupon)
            .select(Projections.fields(AdminCouponListResponseDto.class,
                qCoupon.couponNo,
                qCoupon.name,
                qCoupon.code,
                qCoupon.couponCreatedAt,
                qCoupon.couponExpiredAt,
                qCoupon.amount,
                qCoupon.percent,
                qCoupon.point,
                qCoupon.totalQuantity,
                qCoupon.issuedQuantity,
                qCoupon.isDuplicateUse,
                qCategory.categoryNo,
                qCategory.categoryName,
                qProduct.productNo,
                qProduct.name.as("productName"),
                qCouponType.couponTypeEnum.stringValue().as(COUPON_TYPE)))
            .join(qCoupon.couponType, qCouponType)
            .leftJoin(qCategoryCoupon).on(qCoupon.couponNo.eq(qCategoryCoupon.couponNo))
            .leftJoin(qCategory).on(qCategoryCoupon.category.categoryNo.eq(qCategory.categoryNo))
            .leftJoin(qProductCoupon).on(qCoupon.couponNo.eq(qProductCoupon.couponNo))
            .leftJoin(qProduct).on(qProductCoupon.product.productNo.eq(qProduct.productNo))
            .orderBy(qCoupon.couponNo.desc())
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

    @Override
    public List<AdminCouponListResponseDto> findByAvailableCouponDtoByCouponType(CouponTypeEnum couponTypeEnum) {

        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCategory qCategory = QCategory.category;
        QProduct qProduct = QProduct.product;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;


        JPQLQuery<AdminCouponListResponseDto> jpqlQuery = from(qCoupon)
            .select(Projections.fields(AdminCouponListResponseDto.class,
                qCoupon.couponNo,
                qCoupon.name,
                qCoupon.code,
                qCoupon.couponCreatedAt,
                qCoupon.couponExpiredAt,
                qCoupon.amount,
                qCoupon.percent,
                qCoupon.point,
                qCoupon.totalQuantity,
                qCoupon.issuedQuantity,
                qCoupon.isDuplicateUse,
                qCategory.categoryNo,
                qCategory.categoryName,
                qProduct.productNo,
                qProduct.name.as("productName"),
                qCouponType.couponTypeEnum.stringValue().as(COUPON_TYPE)))
            .join(qCoupon.couponType, qCouponType)
            .leftJoin(qCategoryCoupon).on(qCoupon.couponNo.eq(qCategoryCoupon.couponNo))
            .leftJoin(qCategory).on(qCategoryCoupon.category.categoryNo.eq(qCategory.categoryNo))
            .leftJoin(qProductCoupon).on(qCoupon.couponNo.eq(qProductCoupon.couponNo))
            .leftJoin(qProduct).on(qProductCoupon.product.productNo.eq(qProduct.productNo))
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
    public Page<AdminCouponListResponseDto> findByCouponAtCouponTypeList(Pageable pageable,
                                                                         CouponTypeEnum couponTypeEnum) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCategory qCategory = QCategory.category;
        QProduct qProduct = QProduct.product;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;

        JPQLQuery<Long> jpqlQuery = from(qCoupon)
            .select(qCoupon.count())
            .where(qCouponType.couponTypeEnum.eq(couponTypeEnum));

        List<AdminCouponListResponseDto> couponList = from(qCoupon)
            .select(Projections.fields(AdminCouponListResponseDto.class,
                qCoupon.couponNo,
                qCoupon.name,
                qCoupon.code,
                qCoupon.couponCreatedAt,
                qCoupon.couponExpiredAt,
                qCoupon.amount,
                qCoupon.percent,
                qCoupon.point,
                qCoupon.totalQuantity,
                qCoupon.issuedQuantity,
                qCoupon.isDuplicateUse,
                qCategory.categoryNo,
                qCategory.categoryName,
                qProduct.productNo,
                qProduct.name.as("productName"),
                qCouponType.couponTypeEnum.stringValue().as(COUPON_TYPE)))
            .join(qCoupon.couponType, qCouponType)
            .leftJoin(qCategoryCoupon).on(qCoupon.couponNo.eq(qCategoryCoupon.couponNo))
            .leftJoin(qCategory).on(qCategoryCoupon.category.categoryNo.eq(qCategory.categoryNo))
            .leftJoin(qProductCoupon).on(qCoupon.couponNo.eq(qProductCoupon.couponNo))
            .leftJoin(qProduct).on(qProductCoupon.product.productNo.eq(qProduct.productNo))
            .where(qCouponType.couponTypeEnum.eq(couponTypeEnum))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return PageableExecutionUtils.getPage(couponList, pageable, jpqlQuery::fetchOne);
    }
}
