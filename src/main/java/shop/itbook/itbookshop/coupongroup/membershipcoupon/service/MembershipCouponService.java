package shop.itbook.itbookshop.coupongroup.membershipcoupon.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface MembershipCouponService {
    Long addMembershipCoupon(Long couponNo, String membershipGrade);

    Map<String,List<MembershipCouponResponseDto>> findAvailableMembershipCouponList();

    Long membershipCouponDownload(Long couponNo, Long memberId);
}
