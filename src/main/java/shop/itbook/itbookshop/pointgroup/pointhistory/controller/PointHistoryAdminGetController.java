package shop.itbook.itbookshop.pointgroup.pointhistory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.resultmessageenum.PointHistroyResultMessageEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.impl.PointHistoryServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/point-histories")
@RequiredArgsConstructor
public class PointHistoryAdminGetController {

    private final PointHistoryServiceImpl pointHistoryServiceImpl;

    @GetMapping("/{pointHistoryNo}")
    public void pointHistoryDetails(@PathVariable Long pointHistoryNo) {

    }

    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListDto>>> pointHistoryList(
        @PageableDefault Pageable pageable) {
        Page<PointHistoryListDto> pointHistoryList =
            pointHistoryServiceImpl.findPointHistoryList(pageable);

        return ResponseEntity.ok(new CommonResponseBody(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.POINT_HISTORY_LIST_GET_SUCCESS.getResultMessage())
            , new PageResponse<>(pointHistoryList)));
    }

    @GetMapping("/my-point/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListDto>>> memberPointHistoryList(
        @PathVariable Long memberNo, @PageableDefault Pageable pageable) {
        Page<PointHistoryListDto> pointHistoryList =
            pointHistoryServiceImpl.findMyPointHistoryList(memberNo, pageable);

        return ResponseEntity.ok(new CommonResponseBody(new CommonResponseBody.CommonHeader(
            PointHistroyResultMessageEnum.MY_POINT_HISTORY_LIST_GET_SUCCESS.getResultMessage())
            , new PageResponse<>(pointHistoryList)));
    }
}
