package shop.itbook.itbookshop.pointgroup.pointhistory.service;

import java.util.Optional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryService {

    Optional<PointHistory> findRecentPointHistory(Member member);

    Long findRecentlyPoint(Member member);

    PointHistory getSavedIncreasePointHistory(Member member, Long pointToApply,
                                              PointIncreaseDecreaseContentEnum contentEnum);

    PointHistory getSavedDecreasePointHistory(Member member, Long pointToApply,
                                              PointIncreaseDecreaseContentEnum contentEnum);
}
