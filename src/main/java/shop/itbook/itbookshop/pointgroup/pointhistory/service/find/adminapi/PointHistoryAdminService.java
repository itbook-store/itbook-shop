package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface PointHistoryAdminService {

    Page<PointHistoryListResponseDto> findPointHistoryList(Pageable pageable,
                                                           PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    Page<PointHistoryListResponseDto> findPointHistoryListBySearch(Pageable pageable,
                                                                   PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                                   String searchWord);

    PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsDto(Long pointHistoryNo);

    PointHistoryGradeDetailsResponseDto findPointHistoryGradeDetailsDto(Long pointHistoryNo);

    ReviewResponseDto findReviewResponseDtoForPointHistoryReviewDetails(Long pointHistoryNo);

    PointHistoryCouponDetailsResponseDto findPointHistoryCouponDetailsDto(Long pointHistoryNo);
}
