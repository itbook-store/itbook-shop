package shop.itbook.itbookshop.coupongroup.coupon.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponService {

    Long addCoupon(CouponRequestDto couponRequestDto);

    void deleteCoupon(Long couponNo);

    CouponResponseDto findByCouponResponseDto(String code);

    Page<AdminCouponListResponseDto> findByCouponList(Pageable pageable);

    Page<AdminCouponListResponseDto> findByCouponAtCouponTypeList(Pageable pageable, String couponType);

    Coupon findByCouponEntity(Long couponNo);

    Coupon useCoupon(Coupon coupon);

    List<Coupon> findByAvailableCouponByCouponType(String couponType);

    List<AdminCouponListResponseDto> findByAvailableCouponDtoByCouponType(String couponType);
}
