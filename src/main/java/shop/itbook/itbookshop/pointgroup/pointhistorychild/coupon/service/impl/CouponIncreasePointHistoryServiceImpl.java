package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.impl;

import static shop.itbook.itbookshop.pointgroup.pointhistory.service.impl.PointHistoryServiceImpl.POINT_INCREASE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.repository.CouponIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.CouponIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service.PointIncreaseDecreaseContentService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponIncreasePointHistoryServiceImpl
    implements CouponIncreasePointHistoryService {

    private final PointHistoryService pointHistoryService;
    private final PointIncreaseDecreaseContentService pointIncreaseDecreaseContentService;
    private final CouponIncreasePointHistoryRepository couponIncreasePointHistoryRepository;

    @Override
    @Transactional
    public CouponIncreasePointHistory addCouponIncreasePointHistory(CouponIssue couponIssue,
                                                                    Long pointToApply) {

        Member member = couponIssue.getMember();
        Long remainedPoint = pointHistoryService.getRemainedPointWhenIncrease(member, pointToApply);

        PointIncreaseDecreaseContent pointIncreaseDecreaseContent =
            pointIncreaseDecreaseContentService.findPointIncreaseDecreaseContentThroughContentEnum(
                PointIncreaseDecreaseContentEnum.COUPON);

        PointHistory savedPointHistory = pointHistoryService.savePointHistory(
            new PointHistory(member, pointIncreaseDecreaseContent, pointToApply, remainedPoint,
                POINT_INCREASE));

        CouponIncreasePointHistory couponIncreasePointHistory =
            new CouponIncreasePointHistory(savedPointHistory.getPointHistoryNo(), couponIssue);

        return couponIncreasePointHistoryRepository.save(couponIncreasePointHistory);
    }
}
