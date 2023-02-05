package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi.PointHistoryMemberService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PointHistoryMemberServiceImpl implements PointHistoryMemberService {
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Page<PointHistoryListDto> findMyPointHistoryList(Long memberNo, Pageable pageable,
                                                            PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointHistoryRepository.findMyPointHistoryListDto(memberNo, pageable,
            pointIncreaseDecreaseContentEnum);
    }
}
