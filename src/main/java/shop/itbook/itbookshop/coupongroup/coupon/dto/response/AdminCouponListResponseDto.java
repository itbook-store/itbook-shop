package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 송다혜
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminCouponListResponseDto {
    private Long couponNo;
    private String couponType;
    private String name;
    private String code;
    private Long amount;
    private Integer percent;
    private Long point;
    private LocalDateTime couponCreatedAt;
    private LocalDateTime couponExpiredAt;
    private Integer totalQuantity;
    private Integer issuedQuantity;
    private Boolean isDuplicateUse;
    private Integer categoryNo;
    private String categoryName;
    private String parentCategoryName;
    private Long productNo;
    private String productName;
}
