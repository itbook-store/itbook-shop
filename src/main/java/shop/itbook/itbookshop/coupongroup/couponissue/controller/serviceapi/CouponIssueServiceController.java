package shop.itbook.itbookshop.coupongroup.couponissue.controller.serviceapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.request.CouponIssueNoRequest;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.resultmessageenum.CouponIssueResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;


/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon-issues")
public class CouponIssueServiceController {

    private final CouponIssueService couponIssueService;

    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponseBody<PageResponse<UserCouponIssueListResponseDto>>>
    userCouponIssueList(@PageableDefault Pageable pageable,
                        @PathVariable("memberId") String memberId) {

        Page<UserCouponIssueListResponseDto> couponIssueList =
            couponIssueService.findCouponIssueListByMemberId(pageable, memberId);

        CommonResponseBody<PageResponse<UserCouponIssueListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                new PageResponse(couponIssueList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @PostMapping("/{couponNo}/{memberNo}/add")
    public ResponseEntity<CommonResponseBody<CouponIssueNoRequest>> addCouponIssue(
        @PageableDefault Pageable pageable, @PathVariable("memberNo") Long memberNo,
        @PathVariable("couponNo") Long couponNo) {

        CouponIssueNoRequest couponIssueNoRequest =
            new CouponIssueNoRequest(couponIssueService.addCouponIssueByCoupon(memberNo, couponNo));

        CommonResponseBody<CouponIssueNoRequest> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                couponIssueNoRequest);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    @PutMapping("/{couponIssueNo}/point-coupon-use")
    public ResponseEntity<CommonResponseBody<Void>> useUserPointCoupon(
        @PathVariable Long couponIssueNo) {

        couponIssueService.usePointCouponAndCreatePointHistory(couponIssueNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }
}

