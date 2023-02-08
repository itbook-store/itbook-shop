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
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.resultmessageenum.PointHistroyResultMessageEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.serviceapi.PointHistoryMemberService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/point-histories")
@RequiredArgsConstructor
public class PointHistoryMemberGetController {

    private final PointHistoryMemberService pointHistoryMemberService;

    @GetMapping("/my-point/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListResponseDto>>> memberPointHistoryList(
        @PathVariable Long memberNo, @PageableDefault Pageable pageable,
        @RequestParam(required = false) String content) {
        Page<PointHistoryListResponseDto> pointHistoryList =
            pointHistoryMemberService.findMyPointHistoryList(memberNo, pageable,
                PointIncreaseDecreaseContentEnum.stringToEnum(content));

        return ResponseEntity.ok(new CommonResponseBody(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.MY_POINT_HISTORY_LIST_GET_SUCCESS.getResultMessage())
            , new PageResponse<>(pointHistoryList)));
    }
}
