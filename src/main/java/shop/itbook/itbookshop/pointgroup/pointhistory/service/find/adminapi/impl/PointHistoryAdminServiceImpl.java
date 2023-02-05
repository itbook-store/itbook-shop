package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PointHistoryAdminServiceImpl implements PointHistoryAdminService {

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Page<PointHistoryListDto> findPointHistoryList(Pageable pageable,
                                                          PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointHistoryRepository.findPointHistoryListDto(pageable,
            pointIncreaseDecreaseContentEnum);
    }

    @Override
    public Page<PointHistoryListDto> findPointHistoryListBySearch(Pageable pageable,
                                                                  PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                                  String searchWord) {
        return pointHistoryRepository.findPointHistoryListDtoThroughSearch(pageable,
            pointIncreaseDecreaseContentEnum, searchWord);
    }

}
