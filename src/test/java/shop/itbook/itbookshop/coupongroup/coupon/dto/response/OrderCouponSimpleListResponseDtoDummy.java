package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 송다혜
 * @since 1.0
 */
public class OrderCouponSimpleListResponseDtoDummy {
    public static OrderCouponSimpleListResponseDto getDto(Long couponIssueNo, Long couponNo){
        return new OrderCouponSimpleListResponseDto(couponIssueNo, couponNo, "name",
            "code", 5000L, 10, null, null);
    }

}