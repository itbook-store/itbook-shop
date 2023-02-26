package shop.itbook.itbookshop.coupongroup.couponissue.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * @author 송다혜
 * @since 1.0
 */
public class UserCouponIssueListResponseDtoDummy {
    public static UserCouponIssueListResponseDto  getDto(Long couponIssueNo) {
        return new UserCouponIssueListResponseDto(couponIssueNo,
            "name", "code", 5000L,
             10, 5000L, null,
            null, "couponType",
            "usageStatusName",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            null,
            1L,
            "productName", 1,
            "categoryName",
            "parentCategoryName"
            );
    }
}