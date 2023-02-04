package shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCouponDetailListDto {
    private CouponListResponseDto coupon;
    private CategoryListResponseDto category;
}