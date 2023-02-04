package shop.itbook.itbookshop.coupongroup.couponissue.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.resultmessageenum.CouponIssueResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon/coupon-issue")
public class CouponIssueServiceController {

    private final CouponIssueService couponIssueService;

    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponseBody<PageResponse<UserCouponIssueListResponseDto>>>
        userCouponIssueList(@PageableDefault Pageable pageable, @PathVariable("memberId") String memberId) {

        Page<UserCouponIssueListResponseDto> couponIssueList =
            couponIssueService.findCouponIssueListByMemberId(pageable, memberId);

        CommonResponseBody<PageResponse<UserCouponIssueListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                new PageResponse(couponIssueList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
