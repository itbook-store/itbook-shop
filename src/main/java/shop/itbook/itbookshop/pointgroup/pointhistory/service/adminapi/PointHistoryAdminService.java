package shop.itbook.itbookshop.pointgroup.pointhistory.service.adminapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryAdminService {

    Page<PointHistoryListDto> findPointHistoryList(Pageable pageable,
                                                   PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    Page<PointHistoryListDto> findPointHistoryListBySearch(Pageable pageable,
                                                           PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                           String searchWord);
}
