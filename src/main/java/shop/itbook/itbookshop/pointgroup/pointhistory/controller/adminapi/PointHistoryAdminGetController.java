package shop.itbook.itbookshop.pointgroup.pointhistory.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.resultmessageenum.PointHistroyResultMessageEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/point-histories")
@RequiredArgsConstructor
public class PointHistoryAdminGetController {

    private final PointHistoryAdminService pointHistoryAdminService;


    @GetMapping("/{pointHistoryNo}/coupon-details")
    public ResponseEntity<CommonResponseBody<PointHistoryCouponDetailsResponseDto>> pointHistoryCouponDetails(
        @PathVariable Long pointHistoryNo) {

        PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsResponseDto =
            pointHistoryAdminService.findPointHistoryCouponDetailsDto(pointHistoryNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryCouponDetailsResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/review-details")
    public ResponseEntity<CommonResponseBody<ReviewResponseDto>> pointHistoryReviewDetails(
        @PathVariable Long pointHistoryNo) {

        ReviewResponseDto reviewResponseDto =
            pointHistoryAdminService.findReviewResponseDtoForPointHistoryReviewDetails(
                pointHistoryNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , reviewResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/gift-details")
    public ResponseEntity<CommonResponseBody<PointHistoryGiftDetailsResponseDto>> pointHistoryGiftDetails(
        @PathVariable Long pointHistoryNo) {

        PointHistoryGiftDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryAdminService.findPointHistoryGiftDetailsDto(pointHistoryNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryGiftDetailsResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/grade-details")
    public ResponseEntity<CommonResponseBody<PointHistoryGradeDetailsResponseDto>> pointHistoryGradeDetails(
        @PathVariable Long pointHistoryNo) {

        PointHistoryGradeDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryAdminService.findPointHistoryGradeDetailsDto(pointHistoryNo);

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryGiftDetailsResponseDto));
    }


    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListResponseDto>>> pointHistoryList(
        @PageableDefault Pageable pageable,
        @RequestParam(required = false) String content) {

        Page<PointHistoryListResponseDto> pointHistoryList =
            pointHistoryAdminService.findPointHistoryList(pageable,
                PointIncreaseDecreaseContentEnum.stringToEnum(content));

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_LIST_GET_SUCCESS.getResultMessage())
            , new PageResponse<>(pointHistoryList)));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListResponseDto>>> pointHistoryListBySearch(
        @PageableDefault Pageable pageable,
        @RequestParam(required = false) String content, @RequestParam String searchWord) {

        Page<PointHistoryListResponseDto> pointHistoryList =
            pointHistoryAdminService.findPointHistoryListBySearch(pageable,
                PointIncreaseDecreaseContentEnum.stringToEnum(content), searchWord);

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_LIST_GET_SUCCESS.getResultMessage())
            , new PageResponse<>(pointHistoryList)));
    }

}
