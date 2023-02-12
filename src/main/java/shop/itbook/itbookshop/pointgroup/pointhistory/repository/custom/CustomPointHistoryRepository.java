package shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

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

    PointHistoryGradeDetailsResponseDto findMembershipResponseDtoThroughPointHistory(
        Long pointHistoryNo);

    ReviewResponseDto findPointHistoryReviewDetailsDto(Long pointHistoryNo);

    PointHistoryCouponDetailsResponseDto findPointHistoryCouponDetailsDto(Long pointHistoryNo);

    PointHistoryGiftDetailsResponseDto findMyPointHistoryGiftDetailsResponseDto(Long pointHistoryNo,
                                                                                Long memberNo);

    PointHistoryGradeDetailsResponseDto findMyMembershipResponseDtoThroughPointHistory(
        Long pointHistoryNo, Long memberNo);

    ReviewResponseDto findMyPointHistoryReviewDetailsDto(Long pointHistoryNo, Long memberNo);

    PointHistoryCouponDetailsResponseDto findMyPointHistoryCouponDetailsDto(Long pointHistoryNo,
                                                                            Long memberNo);
}
