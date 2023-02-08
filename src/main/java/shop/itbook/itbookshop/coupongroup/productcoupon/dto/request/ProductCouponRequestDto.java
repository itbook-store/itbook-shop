package shop.itbook.itbookshop.coupongroup.productcoupon.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponRequestDto {
    @NotNull
    Long productNo;
    @NotNull
    CouponRequestDto couponRequestDto;
}
