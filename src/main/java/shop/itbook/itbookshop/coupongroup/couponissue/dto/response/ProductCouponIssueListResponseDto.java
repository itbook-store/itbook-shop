package shop.itbook.itbookshop.coupongroup.couponissue.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponIssueListResponseDto {
    private Long productNo;
    private Long couponIssueNo;
    private LocalDateTime couponExpiredAt;
    private OrderCouponListResponseDto couponListResponseDto;
}
