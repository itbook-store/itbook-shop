package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.exception.MyPointHistoryDetailsNotFoundException;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi.PointHistoryMemberService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
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
public class PointHistoryMemberServiceImpl implements PointHistoryMemberService {
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Page<PointHistoryListResponseDto> findMyPointHistoryList(Long memberNo,
                                                                    Pageable pageable,
                                                                    PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointHistoryRepository.findMyPointHistoryListResponseDto(memberNo, pageable,
            pointIncreaseDecreaseContentEnum);
    }

    @Override
    public PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsDto(
        Long pointHistoryNo, Long memberNo) {


        PointHistoryGiftDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryRepository.findMyPointHistoryGiftDetailsResponseDto(pointHistoryNo,
                memberNo);

        if (Objects.isNull(pointHistoryGiftDetailsResponseDto)) {
            throw new MyPointHistoryDetailsNotFoundException();
        }

        return pointHistoryGiftDetailsResponseDto;
    }

    @Override
    public PointHistoryGradeDetailsResponseDto findPointHistoryGradeDetailsDto(
        Long pointHistoryNo, Long memberNo) {

        PointHistoryGradeDetailsResponseDto myMembershipResponseDtoThroughPointHistory =
            pointHistoryRepository.findMyMembershipResponseDtoThroughPointHistory(pointHistoryNo,
                memberNo);

        if (Objects.isNull(myMembershipResponseDtoThroughPointHistory)) {
            throw new MyPointHistoryDetailsNotFoundException();
        }

        return myMembershipResponseDtoThroughPointHistory;
    }

    @Override
    public ReviewResponseDto findReviewResponseDtoForPointHistoryReviewDetails(Long pointHistoryNo,
                                                                               Long memberNo) {

        ReviewResponseDto myPointHistoryReviewDetailsDto =
            pointHistoryRepository.findMyPointHistoryReviewDetailsDto(pointHistoryNo, memberNo);

        if (Objects.isNull(myPointHistoryReviewDetailsDto)) {
            throw new MyPointHistoryDetailsNotFoundException();
        }

        return myPointHistoryReviewDetailsDto;
    }

    @Override
    public PointHistoryCouponDetailsResponseDto findPointHistoryCouponDetailsDto(
        Long pointHistoryNo, Long memberNo) {

        PointHistoryCouponDetailsResponseDto myPointHistoryCouponDetailsDto =
            pointHistoryRepository.findMyPointHistoryCouponDetailsDto(pointHistoryNo, memberNo);

        if (Objects.isNull(myPointHistoryCouponDetailsDto)) {
            throw new MyPointHistoryDetailsNotFoundException();
        }

        return myPointHistoryCouponDetailsDto;
    }

}
