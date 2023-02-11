package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.request.OrderTotalCouponRequestDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface OrderTotalCouponService {

    Long addOrderTotalCoupon(OrderTotalCouponRequestDto orderTotalCouponRequestDto);

    Page<OrderCouponListResponseDto> findTotalCouponPageList(Pageable pageable);
}
