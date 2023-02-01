package shop.itbook.itbookshop.coupongroup.categorycoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response.CategoryCouponListDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CategoryCouponService {
    Long addCategoryCoupon(CategoryCouponRequestDto couponRequestDto);

    void deleteCategoryCoupon(Long couponNo);

    Page<CategoryCouponListDto> findCategoryCouponList(Pageable pageable);
}
