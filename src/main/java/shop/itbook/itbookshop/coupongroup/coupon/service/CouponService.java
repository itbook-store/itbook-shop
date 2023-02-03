package shop.itbook.itbookshop.coupongroup.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponService {

    Long addCoupon(CouponRequestDto couponRequestDto);

    void deleteCoupon(Long couponNo);

    CouponResponseDto findByCouponResponseDto(String code);

    Page<CouponListResponseDto> findByCouponList(Pageable pageable);
}
