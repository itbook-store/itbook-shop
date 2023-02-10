package shop.itbook.itbookshop.pointgroup.pointhistory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.exception.LackOfPointException;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
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
public class PointHistoryServiceImpl implements PointHistoryService {

    public static final Boolean INCREASE_POINT_HISTORY = Boolean.FALSE;
    public static final Boolean DECREASE_POINT_HISTORY = Boolean.TRUE;

    private final PointHistoryRepository pointHistoryRepository;

    private final PointIncreaseDecreaseContentService pointIncreaseDecreaseContentService;

    private final PointHistoryCommonService pointHistoryCommonService;

    @Override
    @Transactional
    public PointHistory getSavedIncreasePointHistory(Member member, Long pointToApply,
                                                     PointIncreaseDecreaseContentEnum contentEnum) {

        return getSavedPointHistory(member, pointToApply, contentEnum, INCREASE_POINT_HISTORY);
    }

    @Override
    @Transactional
    public PointHistory getSavedDecreasePointHistory(Member member, Long pointToApply,
                                                     PointIncreaseDecreaseContentEnum contentEnum) {

        return getSavedPointHistory(member, pointToApply, contentEnum, DECREASE_POINT_HISTORY);
    }

    private PointHistory getSavedPointHistory(Member member, Long pointToApply,
                                              PointIncreaseDecreaseContentEnum contentEnum,
                                              Boolean isDecrease) {

        Long remainedPointToSave =
            this.getRemainedPointToSave(member, pointToApply, isDecrease);

        PointIncreaseDecreaseContent pointIncreaseDecreaseContent =
            pointIncreaseDecreaseContentService.findPointIncreaseDecreaseContentThroughContentEnum(
                contentEnum);

        PointHistory pointHistoryToSave =
            new PointHistory(member, pointIncreaseDecreaseContent, pointToApply,
                remainedPointToSave,
                isDecrease);
        return pointHistoryRepository.save(pointHistoryToSave);
    }

    private Long getRemainedPointToSave(Member member, Long pointToApply,
                                        boolean isDecrease) {

        Long recentlyRemainedPoint = pointHistoryCommonService.findRecentlyPoint(member);

        if (isDecrease) {

            long remainedPointToSave = recentlyRemainedPoint - pointToApply;
            if (remainedPointToSave < 0) {
                throw new LackOfPointException();
            }

            return remainedPointToSave;
        }

        return recentlyRemainedPoint + pointToApply;
    }
}
