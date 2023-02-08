package shop.itbook.itbookshop.coupongroup.couponissue.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.AlreadyAddedCouponIssueMemberCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.NotPointCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.UnableToCreateCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
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

        CouponIssue couponIssue = makeCouponIssue(member, coupon);
        try {
            couponIssue = couponIssueRepository.save(couponIssue);
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getRootCause();
            String message = rootCause.getMessage();

            if (message.contains("coupon_issue.memberNoAndCouponNo")) {
                throw new AlreadyAddedCouponIssueMemberCouponException();
            }

            throw e;
        }
        return couponIssue.getCouponIssueNo();
    }

    @Override
    public List<CouponIssue> addCouponIssueByCoupons(Long memberNo, String couponType) {

        Member member = memberService.findMemberByMemberNo(memberNo);

        List<Coupon> couponList = couponService.findByAvailableCouponByCouponType(couponType);

        List<CouponIssue> couponIssueList = new ArrayList<>();
        for (Coupon coupon : couponList) {

            try {
                CouponIssue couponIssue = makeCouponIssue(member, coupon);
                couponIssueList.add(couponIssue);
            } catch (UnableToCreateCouponException e) {
                log.error("쿠폰 수량이 부족합니다.");
            }
        }

        return couponIssueRepository.saveAll(couponIssueList);
    }

    public CouponIssue makeCouponIssue(Member member, Coupon coupon) {

        coupon = couponService.useCoupon(coupon);

        UsageStatus usageStatus = usageStatusService.findUsageStatus("사용가능");

        return CouponIssue.builder()
            .member(member)
            .coupon(coupon)
            .usageStatus(usageStatus)
            .couponExpiredAt(coupon.getCouponExpiredAt())
            .build();
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
