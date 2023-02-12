package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryMemberService {
    Page<PointHistoryListResponseDto> findMyPointHistoryList(Long memberNo, Pageable pageable,
                                                             PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum);

    PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsDto(Long pointHistoryNo,
                                                                      Long memberNo);

    PointHistoryGradeDetailsResponseDto findPointHistoryGradeDetailsDto(Long pointHistoryNo,
                                                                        Long memberNo);

    ReviewResponseDto findReviewResponseDtoForPointHistoryReviewDetails(Long pointHistoryNo,
                                                                        Long memberNo);

    PointHistoryCouponDetailsResponseDto findPointHistoryCouponDetailsDto(Long pointHistoryNo,
                                                                          Long memberNo);
}
