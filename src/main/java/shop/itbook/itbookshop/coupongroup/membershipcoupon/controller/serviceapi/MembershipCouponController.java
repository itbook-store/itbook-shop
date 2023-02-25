package shop.itbook.itbookshop.coupongroup.membershipcoupon.controller.serviceapi;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.request.CouponIssueNoRequest;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.resultmessageenum.MembershipCouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.service.MembershipCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/membership-coupons")
public class MembershipCouponController {

    private final MembershipCouponService membershipCouponService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<Map<String, List<MembershipCouponResponseDto>>>> findMembershipCoupon() {

        Map<String, List<MembershipCouponResponseDto>> membershipCouponNoResponseDto =
            membershipCouponService.findAvailableMembershipCouponList();

        CommonResponseBody<Map<String, List<MembershipCouponResponseDto>>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MembershipCouponResultMessageEnum.MEMBERSHIP_COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                membershipCouponNoResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @PostMapping("/{couponNo}/{memberNo}/add")
    public ResponseEntity<CommonResponseBody<CouponIssueNoRequest>> addMembershipCouponIssue(
        @PathVariable("memberNo") Long memberNo,
        @PathVariable("couponNo") Long couponNo) {

        CouponIssueNoRequest couponIssueNoRequest =
            new CouponIssueNoRequest(membershipCouponService.membershipCouponDownload(memberNo, couponNo));

        CommonResponseBody<CouponIssueNoRequest> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MembershipCouponResultMessageEnum.MEMBERSHIP_COUPON_DOWNLOAD_SUCCESS_MESSAGE.getSuccessMessage()),
                couponIssueNoRequest);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }
}
