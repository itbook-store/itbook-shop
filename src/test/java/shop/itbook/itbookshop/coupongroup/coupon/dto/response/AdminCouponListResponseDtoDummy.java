package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public class AdminCouponListResponseDtoDummy {
    public static AdminCouponListResponseDto getDto(Long couponNo) {
        return new AdminCouponListResponseDto(
            couponNo,"couponType", "name", "code", 5000L,
            10, 5000L, null,
            null,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            0,0,false, 1,
            "categoryName",
            "parentCategoryName",
            1L,
            "productName"
            );
    }
}