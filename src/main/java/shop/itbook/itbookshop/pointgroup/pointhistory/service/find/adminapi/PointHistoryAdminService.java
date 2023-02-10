package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryAdminService {

    Page<PointHistoryListResponseDto> findPointHistoryList(Pageable pageable,
                                                           PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    Page<PointHistoryListResponseDto> findPointHistoryListBySearch(Pageable pageable,
                                                                   PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                                   String searchWord);

    PointHistoryGiftDetailsResponseDto findGiftPointHistoryGiftDetailsDto(Long pointHistoryNo);
}
