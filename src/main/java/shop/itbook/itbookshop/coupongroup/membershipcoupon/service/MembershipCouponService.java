package shop.itbook.itbookshop.coupongroup.membershipcoupon.service;


import java.util.List;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface MembershipCouponService {
    Long addMembershipCoupon(Long couponNo, String membershipGrade);

    List<MembershipCouponResponseDto> findAvailableMembershipCouponList();
}
