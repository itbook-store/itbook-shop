package shop.itbook.itbookshop.coupongroup.categorycoupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.QCategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CustomCategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;

/**
 * CustomCategoryCouponRepository 의 구현체 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class CategoryCouponRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCategoryCouponRepository {

    public CategoryCouponRepositoryImpl() {
        super(CategoryCoupon.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AdminCouponListResponseDto> findCategoryCouponList(Pageable pageable) {
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        QCategory qCategory = QCategory.category;
        QCouponType qCouponType = QCouponType.couponType;

        JPQLQuery<CategoryCoupon> jpqlQuery = from(qCategoryCoupon);

        List<AdminCouponListResponseDto> categoryCouponList = from(qCategoryCoupon)
            .select(Projections.fields(AdminCouponListResponseDto.class,
                qCoupon.couponNo, qCoupon.name, qCoupon.code,
                qCoupon.amount, qCoupon.point, qCoupon.percent,
                qCoupon.couponCreatedAt, qCoupon.couponExpiredAt,
                qCoupon.totalQuantity, qCoupon.issuedQuantity,
                qCoupon.isDuplicateUse,
                qCategory.categoryNo, qCategory.categoryName,
                qCategory.parentCategory.categoryName.as("parentCategoryName"),
                qCouponType.couponTypeEnum.stringValue().as("couponType")))
            .innerJoin(qCategoryCoupon.coupon, qCoupon)
            .innerJoin(qCategoryCoupon.category, qCategory)
            .join(qCouponType).on(qCategoryCoupon.coupon.couponType.couponTypeEnum.eq(
                qCouponType.couponTypeEnum))
            .orderBy(qCoupon.couponNo.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(categoryCouponList, pageable, jpqlQuery::fetchCount);

    }

}
