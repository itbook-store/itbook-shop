package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.impl;

import static shop.itbook.itbookshop.pointgroup.pointhistory.service.impl.PointHistoryServiceImpl.DECREASE_POINT_HISTORY;
import static shop.itbook.itbookshop.pointgroup.pointhistory.service.impl.PointHistoryServiceImpl.INCREASE_POINT_HISTORY;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.exception.LackOfPointException;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.repository.GiftIncreaseDecreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.GiftIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftIncreaseDecreasePointHistoryServiceImpl
    implements GiftIncreaseDecreasePointHistoryService {

    private final PointHistoryService pointHistoryService;
    private final GiftIncreaseDecreasePointHistoryRepository
        giftIncreaseDecreasePointHistoryRepository;
    private final PointHistoryCommonService pointHistoryCommonService;

    @Override
    @Transactional
    public GiftIncreaseDecreasePointHistory savePointHistoryAboutGiftDecreaseAndIncrease(
        Member sender, Member receiver, Long pointToApply) {

        GiftIncreaseDecreasePointHistory giftDecreasePointHistoryOfSender =
            saveGiftPointHistory(sender, receiver, pointToApply, DECREASE_POINT_HISTORY);

        saveGiftPointHistory(receiver, sender, pointToApply, INCREASE_POINT_HISTORY);

        return giftDecreasePointHistoryOfSender;
    }

    private GiftIncreaseDecreasePointHistory saveGiftPointHistory(Member mainMember,
                                                                  Member subMember,
                                                                  Long pointToApply,
                                                                  boolean isDecrease) {
        PointHistory savedPointHistory;
        if (isDecrease) {

            Long recentlyRemainedPoint = pointHistoryCommonService.findRecentlyPoint(mainMember);
            long remainedPointToSave = recentlyRemainedPoint - pointToApply;
            if (remainedPointToSave < 0) {
                throw new LackOfPointException();
            }

            savedPointHistory = pointHistoryService.getSavedDecreasePointHistory(mainMember,
                pointToApply,
                PointIncreaseDecreaseContentEnum.GIFT);
        } else {
            savedPointHistory = pointHistoryService.getSavedIncreasePointHistory(mainMember,
                pointToApply,
                PointIncreaseDecreaseContentEnum.GIFT);
        }

        return giftIncreaseDecreasePointHistoryRepository.save(new GiftIncreaseDecreasePointHistory(
            savedPointHistory.getPointHistoryNo(), subMember));
    }

}
