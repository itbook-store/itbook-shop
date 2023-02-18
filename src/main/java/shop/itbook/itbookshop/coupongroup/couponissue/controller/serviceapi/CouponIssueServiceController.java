package shop.itbook.itbookshop.coupongroup.couponissue.controller.serviceapi;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDto;
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

    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<UserCouponIssueListResponseDto>>>
        userCouponIssueList(@PageableDefault Pageable pageable,
                        @PathVariable("memberNo") Long memberNo, @RequestParam(required = false) String usageStatus) {

        Page<UserCouponIssueListResponseDto> page =
            couponIssueService.findCouponIssueListByMemberNo(pageable, memberNo, usageStatus);

        PageResponse<UserCouponIssueListResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<UserCouponIssueListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @PostMapping("/{couponNo}/{memberNo}/add")
    public ResponseEntity<CommonResponseBody<CouponIssueNoRequest>> addCouponIssue(
        @PathVariable("memberNo") Long memberNo,
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
                CouponIssueResultMessageEnum.POINT_COUPON_USE_SUCCESS_MESSAGE.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    @GetMapping("/{memberNo}/order/{productNo}")
    public ResponseEntity<CommonResponseBody<List<OrderCouponSimpleListResponseDto>>> findAvailableProductCategoryCouponByMemberNoAndProductNo(
        @PathVariable Long memberNo, @PathVariable Long productNo) {

        List<OrderCouponSimpleListResponseDto> orderCouponList =
            couponIssueService.findAvailableProductCategoryCouponByMemberNoAndProductNo(memberNo,
                productNo);

        CommonResponseBody<List<OrderCouponSimpleListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                orderCouponList
            );

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    @GetMapping("/{memberNo}/order/total")
    public ResponseEntity<CommonResponseBody<List<OrderCouponSimpleListResponseDto>>> findAvailableProductCategoryCouponByMemberNoAndProductNo(
        @PathVariable Long memberNo) {

        List<OrderCouponSimpleListResponseDto> orderCouponList =
            couponIssueService.findAvailableOrderTotalCouponByMemberNo(memberNo);

        CommonResponseBody<List<OrderCouponSimpleListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                orderCouponList
            );

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }


}

