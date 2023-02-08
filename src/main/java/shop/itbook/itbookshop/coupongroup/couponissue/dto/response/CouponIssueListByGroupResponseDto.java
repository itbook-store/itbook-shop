package shop.itbook.itbookshop.coupongroup.couponissue.dto.response;

import java.util.List;
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
public class CouponIssueListByGroupResponseDto {
    private List<OrderTotalCouponIssueResponseListDto> orderTotalCouponList;
    private List<CategoryCouponIssueListResponseDto> categoryCouponList;
    private List<ProductCouponIssueListResponseDto> productCouponList;
}
