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
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.resultmessageenum.PointHistroyResultMessageEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/point-histories")
@RequiredArgsConstructor
public class PointHistoryAdminGetController {

    private final PointHistoryAdminService pointHistoryAdminService;


    @GetMapping("{pointHistoryNo}/gift-details")
    public ResponseEntity<CommonResponseBody<PointHistoryGiftDetailsResponseDto>> poinHistoryGiftDetails(
        @PathVariable Long pointHistoryNo) {

        PointHistoryGiftDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryAdminService.findPointHistoryGiftDetailsDto(pointHistoryNo);


        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_DETAILS_GET_SUCCESS.getResultMessage())
            , pointHistoryGiftDetailsResponseDto));
    }

    @GetMapping("{pointHistoryNo}/grade-details")
    public ResponseEntity<CommonResponseBody<PointHistoryGradeDetailsResponseDto>> pointHistoryGradeDetails(
        @PathVariable Long pointHistoryNo) {

        PointHistoryGradeDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            pointHistoryAdminService.findMembershipResponseDtoThroughPointHistory(pointHistoryNo);

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
