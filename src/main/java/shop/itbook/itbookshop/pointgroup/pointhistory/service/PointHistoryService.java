package shop.itbook.itbookshop.pointgroup.pointhistory.service;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryService {

    PointHistory findRecentPointHistory(Member member);

    PointHistory savePointHistory(PointHistory pointHistoryToSave);

    Long getRemainedPointWhenIncrease(Member member, Long pointToApply);

    Long getRemainedPointWhenDecrease(Member member, Long pointToApply);
}
