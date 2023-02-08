package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class PointHistoryCommonServiceImpl implements PointHistoryCommonService {

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Optional<PointHistory> findRecentlyPointHistory(Member member) {

        return pointHistoryRepository.findFirstByMemberOrderByPointHistoryNoDesc(member);
    }

    @Override
    public Long findRecentlyPoint(Member member) {
        Optional<PointHistory> recentPointHistory = this.findRecentlyPointHistory(member);

        Long recentlyRemainedPoint = 0L;
        if (recentPointHistory.isPresent()) {
            recentlyRemainedPoint = recentPointHistory.get().getRemainedPoint();
        }

        return recentlyRemainedPoint;
    }
}
