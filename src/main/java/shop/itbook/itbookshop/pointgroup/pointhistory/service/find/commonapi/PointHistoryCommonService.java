package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryCommonService {


    Optional<PointHistory> findRecentlyPointHistory(Member member);

    Long findRecentlyPoint(Member member);
}
