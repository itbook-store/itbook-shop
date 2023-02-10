package shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCouponRequestDto {

    CouponRequestDto couponRequestDto;
    private Integer categoryNo;
}
