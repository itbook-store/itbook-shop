package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;

/**
 * 쿠폰의 정보를 저장하는 response dto 객체 입니다.
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponResponseDto {

    private String name;
    private Long amount;
    private Integer percent;
    private Long point;
    private Long standardAmount;
    private Long maxDiscountAmount;
    private LocalDateTime couponCreatedAt;
    private LocalDateTime couponExpiredAt;
    private LocalDateTime couponModifiedAt;
    private String image;
    private String code;
    private Boolean isDuplicateUse;
    private Integer totalQuantity;
    private Integer issuedQuantity;
    private CouponTypeEnum couponTypeEnum;
}
