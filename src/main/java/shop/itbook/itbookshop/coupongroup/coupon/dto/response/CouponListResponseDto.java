package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import java.time.LocalDateTime;
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
public class CouponListResponseDto {
    private String name;
    private String code;
    private Long amount;
    private Integer percent;
    private Long point;
    private LocalDateTime couponCreatedAt;
    private LocalDateTime couponExpiredAt;
}
