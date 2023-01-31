package shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCouponRequestDto {

    private Long couponNo;
    private Integer categoryNo;
}
