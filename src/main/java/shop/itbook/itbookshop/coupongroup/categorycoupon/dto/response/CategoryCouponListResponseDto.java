package shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCouponListResponseDto {
    private Integer categoryNo;
    private CouponListResponseDto couponListResponseDto;
}
