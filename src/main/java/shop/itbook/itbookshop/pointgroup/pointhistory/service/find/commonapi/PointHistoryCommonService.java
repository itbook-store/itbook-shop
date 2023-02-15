package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi;

import java.util.Optional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryCommonService {


    Optional<PointHistory> findRecentlyPointHistory(Member member);

    Long findRecentlyPoint(Member member);

}
