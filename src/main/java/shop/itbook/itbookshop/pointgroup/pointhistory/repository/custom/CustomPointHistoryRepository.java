package shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomPointHistoryRepository {

    Page<PointHistoryListDto> findPointHistoryListDto(Pageable pageable);

    Page<PointHistoryListDto> findMyPointHistoryListDto(Long memberNo, Pageable pageable);
}
