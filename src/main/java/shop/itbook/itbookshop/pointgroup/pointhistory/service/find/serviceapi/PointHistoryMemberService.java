package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryMemberService {
    Page<PointHistoryListResponseDto> findMyPointHistoryList(Long memberNo, Pageable pageable,
                                                             PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);
}
