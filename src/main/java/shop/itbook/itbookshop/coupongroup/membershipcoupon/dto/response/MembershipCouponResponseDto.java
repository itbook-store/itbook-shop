package shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.ServiceCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipCouponResponseDto {
    private String membershipGrade;
    private ServiceCouponListResponseDto coupon;
}
