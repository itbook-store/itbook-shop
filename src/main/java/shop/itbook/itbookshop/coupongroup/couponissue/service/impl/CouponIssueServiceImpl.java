package shop.itbook.itbookshop.coupongroup.couponissue.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.CategoryCouponApplyService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CategoryCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CouponIssueListByGroupResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.AlreadyAddedCouponIssueMemberCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.CouponIssueNotFoundException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.CouponQuantityExhaustedException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.NotPointCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.UnableToCreateCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.OrderTotalCouponIssueResponseListDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.ProductCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.service.ProductCouponService;
import shop.itbook.itbookshop.coupongroup.productcouponapply.service.ProductCouponApplyService;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
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
    private final CategoryCouponApplyService categoryCouponApplyService;
    private final ProductCouponApplyService productCouponApplyService;
    private final ProductCouponService productCouponService;
    private final OrderProductRepository orderProductRepository;


    @Override
    @Transactional
    public Long addCouponIssueByCoupon(Long memberNo, Long couponNo) {

        Member member = memberService.findMemberByMemberNo(memberNo);

        Coupon coupon = couponService.findByCouponEntity(couponNo);

        CouponIssue couponIssue;
        try {
            couponIssue = makeCouponIssue(member, coupon);
            couponIssue = couponIssueRepository.save(couponIssue);
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getRootCause();
            String message = Objects.requireNonNull(rootCause).getMessage();

            if (message.contains("coupon_issue.memberNoAndCouponNo")) {
                throw new AlreadyAddedCouponIssueMemberCouponException();
            }

            throw e;
        } catch (UnableToCreateCouponException e) {
            throw new CouponQuantityExhaustedException();
        }
        return couponIssue.getCouponIssueNo();
    }

    @Override
    @Transactional
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

    public CouponIssue makeCouponIssue(Member member, Coupon coupon)
        throws UnableToCreateCouponException {

        coupon = couponService.useCoupon(coupon);

        UsageStatus usageStatus = usageStatusService.findUsageStatus("사용가능");

        LocalDateTime expiredDate = coupon.getCouponExpiredAt();
        if (!Objects.isNull(coupon.getUsagePeriod())) {
            expiredDate = LocalDateTime.now().plusDays(coupon.getUsagePeriod());
        }
        return CouponIssue.builder()
            .member(member)
            .coupon(coupon)
            .usageStatus(usageStatus)
            .couponExpiredAt(expiredDate)
            .build();
    }

    @Override
    public Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberNo(
        Pageable pageable, Long memberNo, String usageStatus) {
        if (Objects.isNull(usageStatus)) {
            usageStatus = "any";
        }
        switch (usageStatus) {
            case "사용가능":
                return couponIssueRepository.findAvailableCouponIssueListByMemberNo(pageable,
                    memberNo);
            case "사용불가":
                return couponIssueRepository.findNotAvailableCouponIssueListByMemberNo(pageable,
                    memberNo);
            default:
                List<CouponIssue> periodExpiredCoupons =
                    couponIssueRepository.changePeriodExpiredByMemberNo(memberNo);
                if (!periodExpiredCoupons.isEmpty()) {
                    changePeriodExpiredByMemberNo(periodExpiredCoupons);
                }
                return couponIssueRepository.findCouponIssueListByMemberNo(pageable, memberNo);
        }
    }

    @Transactional
    public void changePeriodExpiredByMemberNo(List<CouponIssue> periodExpiredCoupons) {
        for (CouponIssue couponIssue : periodExpiredCoupons) {
            couponIssue.setUsageStatus(usageStatusService.findUsageStatus(
                UsageStatusEnum.PERIOD_EXPIRED.getUsageStatus()));
        }
        couponIssueRepository.saveAll(periodExpiredCoupons);
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

    @Override
    public CouponIssueListByGroupResponseDto findMemberAvailableCouponIssuesList(Long memberNo) {
        List<OrderTotalCouponIssueResponseListDto> orderTotalCouponList =
            couponIssueRepository.findAvailableOrderTotalCouponIssueByMemberNo(memberNo);

        List<ProductCouponIssueListResponseDto> productCouponList =
            couponIssueRepository.findAvailableProductCouponIssueByMemberNo(memberNo);

        List<CategoryCouponIssueListResponseDto> categoryCouponList =
            couponIssueRepository.findAvailableCategoryCouponIssueByMemberNo(memberNo);
        return new CouponIssueListByGroupResponseDto(orderTotalCouponList, categoryCouponList,
            productCouponList);
    }

    @Override
    public List<OrderCouponSimpleListResponseDto> findAvailableProductCategoryCouponByMemberNoAndProductNo(
        Long memberNo, Long productNo) {

        List<OrderCouponSimpleListResponseDto> productCouponList
            = couponIssueRepository.findAvailableProductCouponByMemberNoAndProductNo(memberNo,
            productNo);
        List<OrderCouponSimpleListResponseDto> categoryCouponList
            = couponIssueRepository.findAvailableCategoryCouponByMemberNoAndProductNo(memberNo,
            productNo);
        productCouponList.addAll(categoryCouponList);

        return productCouponList;
    }

    @Override
    public List<OrderCouponSimpleListResponseDto> findAvailableOrderTotalCouponByMemberNo(
        Long memberNo) {
        return couponIssueRepository.findAvailableTotalCouponByMemberNo(memberNo);
    }

    @Override
    public Page<AdminCouponIssueListResponseDto> findAllCouponIssue(Pageable pageable) {
        return couponIssueRepository.findAllCouponIssue(pageable);
    }

    @Override
    public CouponIssue findCouponIssueByCouponIssueNo(Long couponIssueNo) {
        return couponIssueRepository.findById(couponIssueNo).orElseThrow(
            CouponIssueNotFoundException::new);
    }

    @Override
    @Transactional
    public CouponIssue usingCouponIssue(Long couponIssueNo) {

        CouponIssue couponIssue = findCouponIssueByCouponIssueNo(couponIssueNo);
        couponIssue.setUsageStatus(
            usageStatusService.findUsageStatus(UsageStatusEnum.COMPLETED.getUsageStatus()));
        couponIssue.setCouponUsageCreatedAt(LocalDateTime.now());
        return couponIssueRepository.save(couponIssue);
    }

    @Override
    @Transactional
    public CouponIssue cancelCouponIssue(Long couponIssueNo) {

        CouponIssue couponIssue = findCouponIssueByCouponIssueNo(couponIssueNo);
        UsageStatus usageStatus =
            usageStatusService.findUsageStatus(UsageStatusEnum.AVAILABLE.getUsageStatus());

        couponIssue.setUsageStatus(usageStatus);
        couponIssue.setCouponUsageCreatedAt(null);

        productCouponApplyService.cancelProductCouponApplyAndChangeCouponIssue(couponIssueNo);
        categoryCouponApplyService.cancelCategoryCouponApplyAndChangeCouponIssues(couponIssueNo);

        return couponIssueRepository.save(couponIssue);
    }


    @Override
    @Transactional
    public void saveCouponApplyAboutCategoryAndProduct(Long couponIssueNo, Long orderProductNo) {
        CouponIssue couponIssue = couponIssueRepository.findByIdFetchJoin(couponIssueNo);
        usingCouponIssue(couponIssueNo);

        Optional<OrderProduct> optionalOrderProduct =
            orderProductRepository.findById(orderProductNo);
        OrderProduct orderProduct = null;
        if (optionalOrderProduct.isPresent()) {
            orderProduct = optionalOrderProduct.get();
        }

        if (productCouponService.findByProductCoupon(couponIssue.getCoupon().getCouponNo()) !=
            null) {
            productCouponApplyService.saveProductCouponApplyAndChangeCouponIssue(couponIssueNo,
                orderProduct);
        } else {
            categoryCouponApplyService.saveCategoryCouponApplyAndChangeCouponIssues(couponIssueNo,
                orderProduct);
        }
    }

    @Override
    public Page<AdminCouponIssueListResponseDto> findCouponIssueSearch(Pageable pageable,
                                                                       String searchTarget,
                                                                       String keyword) {
        switch (searchTarget) {
            case "memberId":
                return couponIssueRepository.findCouponIssueSearchMemberId(pageable, keyword);
            case "couponName":
                return couponIssueRepository.findCouponIssueSearchCouponName(pageable, keyword);
            case "couponCode":
                return couponIssueRepository.findCouponIssueSearchCouponCode(pageable, keyword);
            default:
                return couponIssueRepository.findAllCouponIssue(pageable);
        }
    }
}
