package shop.itbook.itbookshop.pointgroup.pointhistory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryService {

    Page<PointHistoryListDto> findPointHistoryList(Pageable pageable,
                                                   PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    Page<PointHistoryListDto> findMyPointHistoryList(Long memberNo, Pageable pageable,
                                                     PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    Page<PointHistoryListDto> findPointHistoryListBySearch(Pageable pageable,
                                                           PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                           String searchWord);
}
