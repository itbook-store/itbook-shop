package shop.itbook.itbookshop.coupongroup.couponissue.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon-issues")
public class CouponIssueAdminController {

    private final CouponIssueService couponIssueService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<PageResponse<AdminCouponIssueListResponseDto>>> allCouponIssueList(
        Pageable pageable) {

        Page<AdminCouponIssueListResponseDto> page =
            couponIssueService.findAllCouponIssue(pageable);

        PageResponse<AdminCouponIssueListResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<AdminCouponIssueListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
