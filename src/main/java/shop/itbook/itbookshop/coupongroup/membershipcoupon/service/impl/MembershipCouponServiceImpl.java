package shop.itbook.itbookshop.coupongroup.membershipcoupon.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.entity.MembershipCoupon;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.repository.MembershipCouponRepository;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.service.MembershipCouponService;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipCouponServiceImpl implements MembershipCouponService {

    private final MembershipCouponRepository membershipCouponRepository;
    private final MembershipService membershipService;

    @Override
    @Transactional
    public Long addMembershipCoupon(Long couponNo, String membershipGrade) {
        Membership membership = membershipService.findMembershipByMembershipGrade(membershipGrade);

        MembershipCoupon membershipCoupon = new MembershipCoupon(couponNo, membership);

        return membershipCouponRepository.save(membershipCoupon).getCouponNo();
    }

    @Override
    public List<MembershipCouponResponseDto> findAvailableMembershipCouponList() {
        return membershipCouponRepository.findAvailableMembershipCouponList();
    }
}
