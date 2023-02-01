package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 조회 페이지에 반환한 값을 담을 response dto 객체입니다.
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponListResponseDto {
    private Long couponNo;
    private String name;
    private String code;
    private Long amount;
    private Integer percent;
    private Long point;
    private LocalDateTime couponCreatedAt;
    private LocalDateTime couponExpiredAt;
}
