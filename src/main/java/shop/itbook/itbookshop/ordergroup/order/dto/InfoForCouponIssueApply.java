package shop.itbook.itbookshop.ordergroup.order.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 최겸준
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class InfoForCouponIssueApply {
    private Long couponIssueNo;
    private Long orderProductNo;

    public InfoForCouponIssueApply(Long couponIssueNo, Long orderProductNo) {
        this.couponIssueNo = couponIssueNo;
        this.orderProductNo = orderProductNo;
    }
}
