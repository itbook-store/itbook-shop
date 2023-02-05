package shop.itbook.itbookshop.pointgroup.pointhistory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryServiceImpl implements PointHistoryService {

    public static final Boolean POINT_INCREASE = Boolean.FALSE;
    public static final Boolean POINT_DECREASE = Boolean.TRUE;

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public PointHistory findRecentPointHistory(Member member) {

        return pointHistoryRepository.findFirstByMemberOrderByPointHistoryNoDesc(member);
    }

    @Override
    @Transactional
    public PointHistory savePointHistory(PointHistory pointHistoryToSave) {
        return pointHistoryRepository.save(pointHistoryToSave);
    }

    @Override
    public Long getRemainedPointWhenIncrease(Member member, Long pointToApply) {

        PointHistory recentPointHistory = this.findRecentPointHistory(member);
        Long remainedPoint = recentPointHistory.getRemainedPoint();

        return remainedPoint + pointToApply;
    }

    @Override
    public Long getRemainedPointWhenDecrease(Member member, Long pointToApply) {

        PointHistory recentPointHistory = this.findRecentPointHistory(member);
        Long remainedPoint = recentPointHistory.getRemainedPoint();

        return remainedPoint - pointToApply;
    }
}
