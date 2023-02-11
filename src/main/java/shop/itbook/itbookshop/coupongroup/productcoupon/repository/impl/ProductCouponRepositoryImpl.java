package shop.itbook.itbookshop.coupongroup.productcoupon.repository.impl;

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
    public Page<OrderCouponListResponseDto> findProductCouponPageList(Pageable pageable) {
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        QProduct qProduct = QProduct.product;
        QCouponType qCouponType = QCouponType.couponType;

        JPQLQuery<ProductCoupon> jpqlQuery = from(qProductCoupon);

        List<OrderCouponListResponseDto> productCouponList = jpqlQuery
            .select(Projections.fields(OrderCouponListResponseDto.class,
                qCoupon.couponNo,
                qCoupon.amount, qCoupon.point, qCoupon.percent,
                qCoupon.name, qCoupon.code, qCoupon.couponCreatedAt, qCoupon.couponExpiredAt,
                qProduct.productNo, qProduct.name,
                qCouponType.couponTypeEnum.stringValue().as("couponType")))
            .join(qCoupon.couponType, qCouponType)
            .innerJoin(qProductCoupon.coupon, qCoupon)
            .innerJoin(qProductCoupon.product, qProduct)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(productCouponList, pageable, jpqlQuery::fetchCount);
    }
}
