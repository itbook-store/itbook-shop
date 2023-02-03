package shop.itbook.itbookshop.pointgroup.pointhistory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Page<PointHistoryListDto> findPointHistoryList(Pageable pageable) {

        return pointHistoryRepository.findPointHistoryListDto(pageable);
    }

    @Override
    public Page<PointHistoryListDto> findMyPointHistoryList(Long memberNo, Pageable pageable) {
        return pointHistoryRepository.findMyPointHistoryListDto(memberNo, pageable);
    }
}
