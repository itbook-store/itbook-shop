package shop.itbook.itbookshop.pointgroup.pointhistory.controller.serviceapi;

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
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.resultmessageenum.PointHistroyResultMessageEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
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
@RestController
@RequestMapping("/api/point-histories")
@RequiredArgsConstructor
public class PointHistoryMemberGetController {

    private final PointHistoryMemberService pointHistoryMemberService;

    @GetMapping("/{pointHistoryNo}/my-order-details/member-no/{memberNo}")
    public ResponseEntity<CommonResponseBody<OrderDetailsResponseDto>> pointHistoryOrderDetails(
        @PathVariable Long pointHistoryNo, @PathVariable Long memberNo) {

        OrderDetailsResponseDto orderDetailsResponseDto =
            pointHistoryMemberService.findPointHistoryOrderDetailsResponseDto(pointHistoryNo,
                memberNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , orderDetailsResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/my-coupon-details/member-no/{memberNo}")
    public ResponseEntity<CommonResponseBody<PointHistoryCouponDetailsResponseDto>> pointHistoryCouponDetails(
        @PathVariable Long pointHistoryNo, @PathVariable Long memberNo) {

        PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsResponseDto =
            pointHistoryMemberService.findPointHistoryCouponDetailsDto(pointHistoryNo, memberNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryCouponDetailsResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/my-review-details/member-no/{memberNo}")
    public ResponseEntity<CommonResponseBody<ReviewResponseDto>> pointHistoryReviewDetails(
        @PathVariable Long pointHistoryNo, @PathVariable Long memberNo) {

        ReviewResponseDto reviewResponseDto =
            pointHistoryMemberService.findReviewResponseDtoForPointHistoryReviewDetails(
                pointHistoryNo, memberNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , reviewResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/my-gift-details/member-no/{memberNo}")
    public ResponseEntity<CommonResponseBody<PointHistoryGiftDetailsResponseDto>> pointHistoryGiftDetails(
        @PathVariable Long pointHistoryNo, @PathVariable Long memberNo) {

        PointHistoryGiftDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryMemberService.findPointHistoryGiftDetailsDto(pointHistoryNo, memberNo);

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryGiftDetailsResponseDto));
    }

    @GetMapping("/{pointHistoryNo}/my-grade-details/member-no/{memberNo}")
    public ResponseEntity<CommonResponseBody<PointHistoryGradeDetailsResponseDto>> pointHistoryGradeDetails(
        @PathVariable Long pointHistoryNo, @PathVariable Long memberNo) {

        PointHistoryGradeDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryMemberService.findPointHistoryGradeDetailsDto(pointHistoryNo, memberNo);

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryGiftDetailsResponseDto));
    }

    @GetMapping("/my-point/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListResponseDto>>> memberPointHistoryList(
        @PathVariable Long memberNo, @PageableDefault Pageable pageable,
        @RequestParam(required = false) String content) {
        Page<PointHistoryListResponseDto> pointHistoryList =
            pointHistoryMemberService.findMyPointHistoryList(memberNo, pageable,
                PointIncreaseDecreaseContentEnum.stringToEnum(content));

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.MY_POINT_HISTORY_LIST_GET_SUCCESS.getResultMessage())
            , new PageResponse<>(pointHistoryList)));
    }


}
