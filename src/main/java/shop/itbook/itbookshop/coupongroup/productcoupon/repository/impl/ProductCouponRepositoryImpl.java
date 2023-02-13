package shop.itbook.itbookshop.coupongroup.productcoupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.QProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.CustomProductCouponRepository;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public class ProductCouponRepositoryImpl extends QuerydslRepositorySupport
    implements CustomProductCouponRepository {

    public ProductCouponRepositoryImpl() {
        super(ProductCoupon.class);
    }

    @Override
    public Page<AdminCouponListResponseDto> findProductCouponPageList(Pageable pageable) {
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        QProduct qProduct = QProduct.product;
        QCouponType qCouponType = QCouponType.couponType;

        JPQLQuery<ProductCoupon> jpqlQuery = from(qProductCoupon);

        List<AdminCouponListResponseDto> productCouponList = jpqlQuery
            .select(Projections.fields(AdminCouponListResponseDto.class,
                qCoupon.couponNo, qCoupon.name, qCoupon.code,
                qCoupon.amount, qCoupon.point, qCoupon.percent,
                qCoupon.couponCreatedAt, qCoupon.couponExpiredAt,
                qCoupon.totalQuantity,
                qCoupon.issuedQuantity,
                qCoupon.isDuplicateUse,
                qProduct.productNo, qProduct.name.as("productName"),
                qCouponType.couponTypeEnum.stringValue().as("couponType")))
            .join(qCouponType).on(qProductCoupon.coupon.couponType.couponTypeEnum.eq(
                qCouponType.couponTypeEnum))
            .innerJoin(qProductCoupon.coupon, qCoupon)
            .innerJoin(qProductCoupon.product, qProduct)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(productCouponList, pageable, jpqlQuery::fetchCount);
    }

    @Override
    public ProductCoupon findByProductCouponByCouponNo(Long couponNo) {
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        QProduct qProduct = QProduct.product;

        return from(qProductCoupon)
            .select(qProductCoupon)
            .join(qProductCoupon.coupon, qCoupon)
            .join(qProductCoupon.product, qProduct)
            .where(qProductCoupon.couponNo.eq(couponNo))
            .fetchOne();

    }
}
