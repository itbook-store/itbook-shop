package shop.itbook.itbookshop.coupongroup.productcoupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponRequestDto {
    Long productNo;
    Long couponNo;
}
