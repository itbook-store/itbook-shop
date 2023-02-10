package shop.itbook.itbookshop.membergroup.membershiphistory.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookshop.membergroup.membershiphistory.resultmessageenum.MembershipHistoryResultMessageEnum;
import shop.itbook.itbookshop.membergroup.membershiphistory.service.MembershipHistoryService;

/**
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/membership-history")
@RequiredArgsConstructor
public class MembershipHistoryController {

    private final MembershipHistoryService membershipHistoryService;

    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<List<MembershipHistoryResponseDto>>> MembershipHistoryList(
        @PathVariable("memberNo") Long memberNo) {

        CommonResponseBody<List<MembershipHistoryResponseDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MembershipHistoryResultMessageEnum.MEMBERSHIP_HISTORY_LIST_SUCCESS.getMessage()),
                membershipHistoryService.getMembershipHistories(memberNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
