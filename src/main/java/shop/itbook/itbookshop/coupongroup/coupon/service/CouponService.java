package shop.itbook.itbookshop.coupongroup.coupon.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponService {

    Long addCoupon(CouponRequestDto couponRequestDto);

    @Transactional
    void deleteCoupon(Long couponNo);

    CouponResponseDto findByCouponResponseDto(String code);

    List<CouponListResponseDto> findByCouponList();
}
