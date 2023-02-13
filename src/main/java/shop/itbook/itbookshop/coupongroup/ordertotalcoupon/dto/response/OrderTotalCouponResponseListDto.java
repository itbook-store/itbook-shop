package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTotalCouponResponseListDto {
    private OrderCouponListResponseDto orderCouponListResponseDto;
}
