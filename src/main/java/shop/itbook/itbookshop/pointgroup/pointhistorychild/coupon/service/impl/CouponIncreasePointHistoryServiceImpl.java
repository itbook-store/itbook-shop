package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.repository.CouponIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.CouponIncreasePointHistoryService;
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
    private final CouponIncreasePointHistoryRepository couponIncreasePointHistoryRepository;

    @Override
    @Transactional
    public CouponIncreasePointHistory savePointHistoryAboutCouponIncrease(Member member,
                                                                          CouponIssue couponIssue,
                                                                          Long pointToApply) {

        PointHistory savedPointHistory =
            pointHistoryService.getSavedIncreasePointHistory(member, pointToApply,
                PointIncreaseDecreaseContentEnum.COUPON);

        CouponIncreasePointHistory couponIncreasePointHistory =
            new CouponIncreasePointHistory(savedPointHistory.getPointHistoryNo(), couponIssue);

        return couponIncreasePointHistoryRepository.save(couponIncreasePointHistory);
    }
}
