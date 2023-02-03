package shop.itbook.itbookshop.coupongroup.couponissue.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.exception.CouponNotFoundException;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CouponIssueServiceImpl implements CouponIssueService {

    private final CouponIssueRepository couponIssueRepository;
    private final UsageStatusService usageStatusService;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public Long addCouponIssueByNormalCoupon(String memberId, Long couponNo) {

        Member member = memberRepository.findByMemberIdReceiveMember(memberId)
            .orElseThrow(MemberNotFoundException::new);

        Coupon coupon = couponRepository.findById(couponNo)
            .orElseThrow(CouponNotFoundException::new);

        UsageStatus usageStatus = usageStatusService.findUsageStatus("사용가능");

        CouponIssue couponIssue = CouponIssue.builder()
            .member(member)
            .coupon(coupon)
            .usageStatus(usageStatus)
            .couponExpiredAt(coupon.getCouponExpiredAt())
            .build();
        return couponIssueRepository.save(couponIssue).getCouponIssueNo();
    }

    @Override
    @Transactional
    public Integer addCouponIssueByWelcomeCoupon(Member member) {

        List<Coupon> welcomeCouponList = couponRepository.findByAvailableWelcomeCoupon();

        UsageStatus usageStatus = usageStatusService.findUsageStatus("사용가능");

        for (Coupon coupon : welcomeCouponList) {

            CouponIssue couponIssue = CouponIssue.builder()
                .coupon(coupon)
                .member(member)
                .usageStatus(usageStatus)
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .build();
            Long no = couponIssueRepository.save(couponIssue).getCouponIssueNo();
        }
        return 1;
    }
}
