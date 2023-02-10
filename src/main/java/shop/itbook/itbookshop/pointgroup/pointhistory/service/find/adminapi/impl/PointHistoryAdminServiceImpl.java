package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryAdminServiceImpl implements PointHistoryAdminService {

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Page<PointHistoryListResponseDto> findPointHistoryList(Pageable pageable,
                                                                  PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointHistoryRepository.findPointHistoryListResponseDto(pageable,
            pointIncreaseDecreaseContentEnum);
    }

    @Override
    public Page<PointHistoryListResponseDto> findPointHistoryListBySearch(Pageable pageable,
                                                                          PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                                          String searchWord) {
        return pointHistoryRepository.findPointHistoryListResponseDtoThroughSearch(pageable,
            pointIncreaseDecreaseContentEnum, searchWord);
    }

    @Override
    public PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsDto(
        Long pointHistoryNo) {


        return pointHistoryRepository.findPointHistoryGiftDetailsResponseDto(pointHistoryNo);
    }

    @Override
    public PointHistoryGradeDetailsResponseDto findMembershipResponseDtoThroughPointHistory(
        Long pointHistoryNo) {

        return pointHistoryRepository.findMembershipResponseDtoThroughPointHistory(pointHistoryNo);
    }

    @Override
    public ReviewResponseDto findPointHistoryReviewDetailsDto(Long pointHistoryNo) {

        return pointHistoryRepository.findPointHistoryReviewDetailsDto(pointHistoryNo);
    }
}
