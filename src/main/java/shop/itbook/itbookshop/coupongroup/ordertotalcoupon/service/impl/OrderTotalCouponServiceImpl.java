package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.request.OrderTotalCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service.OrderTotalCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderTotalCouponServiceImpl implements OrderTotalCouponService {

    private final OrderTotalCouponRepository orderTotalCouponRepository;
    private final CouponService couponService;

    @Override
    @Transactional
    public Long addOrderTotalCoupon(OrderTotalCouponRequestDto orderTotalCouponRequestDto) {
        Long couponNo = couponService.addCoupon(orderTotalCouponRequestDto.getCouponRequestDto());
        return orderTotalCouponRepository.save(new OrderTotalCoupon(couponNo)).getCouponNo();
    }

    @Override
    public Page<CouponListResponseDto> findTotalCouponPageList(Pageable pageable) {
        return orderTotalCouponRepository.findTotalCouponPageList(pageable);
    }
}
