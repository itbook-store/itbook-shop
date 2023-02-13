package shop.itbook.itbookshop.ordergroup.order.dto;

import java.util.List;
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
public class CouponApplyDto {
    private List<InfoForCouponIssueApply> infoForCouponIssueApplyList;

    public CouponApplyDto(List<InfoForCouponIssueApply> infoForCouponIssueApplyList) {
        this.infoForCouponIssueApplyList = infoForCouponIssueApplyList;
    }
}
