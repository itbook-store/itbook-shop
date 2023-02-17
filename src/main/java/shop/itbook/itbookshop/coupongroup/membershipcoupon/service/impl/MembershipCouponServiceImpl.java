package shop.itbook.itbookshop.coupongroup.membershipcoupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.exception.CouponNotFoundException;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.entity.MembershipCoupon;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.exception.NotMatchCouponException;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.repository.MembershipCouponRepository;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.service.MembershipCouponService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
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
    private final CouponIssueService couponIssueService;
    private final MemberService memberService;

    @Override
    @Transactional
    public Long addMembershipCoupon(Long couponNo, String membershipGrade) {
        Membership membership = membershipService.findMembershipByMembershipGrade(membershipGrade);

        MembershipCoupon membershipCoupon = new MembershipCoupon(couponNo, membership);

        return membershipCouponRepository.save(membershipCoupon).getCouponNo();
    }

    @Override
    public List<List<MembershipCouponResponseDto>> findAvailableMembershipCouponList() {
        List<MembershipResponseDto> membershipGrade = membershipService.findMembershipList();
        List<List<MembershipCouponResponseDto>> membershipCoupons = new ArrayList<>();
        for (MembershipResponseDto grade : membershipGrade) {
            membershipCoupons.add(membershipCouponRepository.findAvailableMembershipCouponList(
                grade.getMembershipGrade()));
        }
        return membershipCoupons;
    }

    @Override
    @Transactional
    public Long membershipCouponDownload(Long memberNo, Long couponNo) {
        Member member = memberService.findMemberByMemberNo(memberNo);

        MembershipCoupon membershipCoupon =
            membershipCouponRepository.findById(couponNo).orElseThrow(CouponNotFoundException::new);

        if (!Objects.equals(member.getMembership().getMembershipNo(),
            membershipCoupon.getMembership().getMembershipNo())) {
            throw new NotMatchCouponException();
        }
        return couponIssueService.addCouponIssueByCoupon(memberNo, couponNo);

    }
}
