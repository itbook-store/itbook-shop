package shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CategoryCouponListDto {
    private CouponListResponseDto coupon;
    private CategoryListResponseDto category;
}
