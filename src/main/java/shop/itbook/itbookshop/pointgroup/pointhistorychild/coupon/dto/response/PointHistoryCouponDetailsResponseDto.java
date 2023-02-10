package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryCouponDetailsResponseDto {

    private String memberId;
    private String memberName;
    private String couponName;
    private String couponType;
    private Long couponPoint;
    private String couponCode;
    private Boolean isDuplicateUse;
    private Long point;
    private Long remainedPoint;
    private LocalDateTime historyCreatedAt;
    private Boolean isDecrease;
}
