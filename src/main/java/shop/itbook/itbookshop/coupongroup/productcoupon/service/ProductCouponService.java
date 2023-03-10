package shop.itbook.itbookshop.coupongroup.productcoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductCouponService {
    Long addProductCoupon(ProductCouponRequestDto productCouponRequestDto);

    Page<AdminCouponListResponseDto> findProductCouponPageList(Pageable pageable);

    ProductCoupon findByProductCoupon(Long couponNo);
}
