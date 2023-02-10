package shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomPointHistoryRepository {

    Page<PointHistoryListResponseDto> findPointHistoryListResponseDto(Pageable pageable,
                                                                      PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    Page<PointHistoryListResponseDto> findPointHistoryListResponseDtoThroughSearch(
        Pageable pageable,
        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
        String searchWord);

    Page<PointHistoryListResponseDto> findMyPointHistoryListResponseDto(Long memberNo,
                                                                        Pageable pageable,
                                                                        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsResponseDto(Long pointHistoryNo);
}
