package shop.itbook.itbookshop.coupongroup.productcoupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
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
    public Page<CouponListResponseDto> findProductCouponList(Pageable pageable) {
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        QProduct qProduct = QProduct.product;
        JPQLQuery<ProductCoupon> jpqlQuery = from(qProductCoupon);

        JPQLQuery<CouponListResponseDto> jpqlQuery1 = jpqlQuery
            .select(Projections.fields(CouponListResponseDto.class,
                qCoupon.couponNo,
                qCoupon.amount, qCoupon.point, qCoupon.percent,
                qCoupon.name, qCoupon.code, qCoupon.couponCreatedAt, qCoupon.couponExpiredAt,
                qProduct.productNo, qProduct.name))
            .innerJoin(qProductCoupon.coupon, qCoupon)
            .innerJoin(qProductCoupon.product, qProduct)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        List<CouponListResponseDto> productCouponList = jpqlQuery1
            .fetch();

        return PageableExecutionUtils.getPage(productCouponList, pageable, jpqlQuery::fetchCount);

    }
}
