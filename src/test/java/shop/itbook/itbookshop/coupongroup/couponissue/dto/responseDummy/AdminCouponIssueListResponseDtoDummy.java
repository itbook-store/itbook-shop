package shop.itbook.itbookshop.coupongroup.couponissue.dto.responseDummy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public class AdminCouponIssueListResponseDtoDummy {
    public static AdminCouponIssueListResponseDto getDto(Long couponIssueNo, Long memberNo, Long couponNo) {
        return new AdminCouponIssueListResponseDto(couponIssueNo, memberNo, "memberId",
            couponNo, "name", "code", 5000L,
            5000L, 10, null,
            null, "couponType",
            1L,
            "productName", 1,
            "categoryName",
            "parentCategoryName",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            null);
    }
}