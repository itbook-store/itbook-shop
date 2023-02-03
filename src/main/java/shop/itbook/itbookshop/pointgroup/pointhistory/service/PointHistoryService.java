package shop.itbook.itbookshop.pointgroup.pointhistory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryService {
    Page<PointHistoryListDto> findPointHistoryList(Pageable pageable);

    Page<PointHistoryListDto> findMyPointHistoryList(Long memberNo, Pageable pageable);
}
