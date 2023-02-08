package shop.itbook.itbookshop.coupongroup.couponissue.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response.CategoryCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.response.OrderTotalCouponResponseListDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.response.ProductCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponIssueListByGroupResponseDto {
    private List<OrderTotalCouponResponseListDto> orderTotalCouponList;
    private List<CategoryCouponListResponseDto> categoryCouponList;
    private List<ProductCouponListResponseDto> productCouponList;
}
