package shop.itbook.itbookshop.ordergroup.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDto {
    private Long productNo;
    private Integer productCnt;
//    private Long categoryCouponIssueNo;
//    private Long productCouponIssueNo;

    private Long couponIssueNo;
}
