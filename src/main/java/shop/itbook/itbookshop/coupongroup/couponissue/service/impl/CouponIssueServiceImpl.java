package shop.itbook.itbookshop.coupongroup.couponissue.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.NotPointCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.CouponIncreasePointHistoryService;

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
    private final MemberService memberService;
    private final CouponService couponService;
    private final CouponIncreasePointHistoryService couponIncreasePointHistoryService;

    @Override
    @Transactional
    public Long addCouponIssueByCoupon(Long memberNo, Long couponNo) {

        Member member = memberService.findMemberByMemberNo(memberNo);

        Coupon coupon = couponService.findByCouponEntity(couponNo);
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
    public List<CouponIssue> addCouponIssueByCoupons(Long memberNo, String couponType) {

        Member member = memberService.findMemberByMemberNo(memberNo);

        List<Coupon> couponList = couponService.findByAvailableCouponByCouponType(couponType);

        UsageStatus usageStatus = usageStatusService.findUsageStatus("사용가능");

        List<CouponIssue> couponIssueList = new ArrayList<>();
        for (Coupon coupon : couponList) {

            CouponIssue couponIssue = CouponIssue.builder()
                .coupon(coupon)
                .member(member)
                .usageStatus(usageStatus)
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .build();
            couponIssueList.add(couponIssue);
        }

        return couponIssueRepository.saveAll(couponIssueList);
    }

    @Override
    public Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberId(
        Pageable pageable, String memberId) {
        return couponIssueRepository.findCouponIssueListByMemberId(pageable, memberId);
    }

    @Override
    @Transactional
    public void usePointCouponAndCreatePointHistory(Long couponIssueNo) {
        CouponIssue couponIssue = couponIssueRepository.findByIdFetchJoin(couponIssueNo);
        Coupon coupon = couponIssue.getCoupon();
        if (coupon.getPoint() == 0 || coupon.getPercent() != 0 || coupon.getAmount() != 0) {
            throw new NotPointCouponException();
        }
        couponIncreasePointHistoryService.savePointHistoryAboutCouponIncrease(
            couponIssue.getMember(), couponIssue, couponIssue.getCoupon().getPoint());
        UsageStatus usageStatus =
            usageStatusService.findUsageStatus(UsageStatusEnum.COMPLETED.getUsageStatus());
        couponIssue.setUsageStatus(usageStatus);
        couponIssue.setCouponUsageCreatedAt(LocalDateTime.now());
        couponIssueRepository.save(couponIssue);
    }
}
