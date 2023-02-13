package shop.itbook.itbookshop.coupongroup.productcoupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomProductCouponRepository {
    Page<AdminCouponListResponseDto> findProductCouponPageList(Pageable pageable);

    ProductCoupon findByProductCouponByCouponNo(Long couponNo);
}
