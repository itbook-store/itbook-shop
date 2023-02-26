package shop.itbook.itbookshop.coupongroup.membershipcoupon.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponNoResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.service.MembershipCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/membership-coupons")
public class MembershipCouponAdminController {

    private final MembershipCouponService membershipCouponService;

    @PostMapping("/{couponNo}/add")
    public ResponseEntity<CommonResponseBody<MembershipCouponNoResponseDto>> addMembershipCoupon(
        @PathVariable Long couponNo, @RequestParam String membershipGrade) {

        MembershipCouponNoResponseDto membershipCouponNoResponseDto =
            new MembershipCouponNoResponseDto(membershipCouponService.addMembershipCoupon(couponNo, membershipGrade));

        CommonResponseBody<MembershipCouponNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CouponResultMessageEnum.COUPON_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            membershipCouponNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
