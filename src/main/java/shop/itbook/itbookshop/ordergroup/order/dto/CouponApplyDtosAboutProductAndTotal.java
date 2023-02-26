package shop.itbook.itbookshop.ordergroup.order.dto;

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
public class CouponApplyDtosAboutProductAndTotal {
    CouponApplyDto productAndCategoryCouponApplyDto;
    CouponApplyDto orderTotalCouponApplyDto;
}
