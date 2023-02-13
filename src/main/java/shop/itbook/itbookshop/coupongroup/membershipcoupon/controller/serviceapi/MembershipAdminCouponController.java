package shop.itbook.itbookshop.coupongroup.membershipcoupon.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
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
public class MembershipAdminCouponController {

    private final MembershipCouponService membershipCouponService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<List<MembershipCouponResponseDto>>> addMembershipCoupon() {

        List<MembershipCouponResponseDto> membershipCouponNoResponseDto =
            membershipCouponService.findAvailableMembershipCouponList();

        CommonResponseBody<List<MembershipCouponResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MembershipCouponResultMessageEnum.MEMBERSHIP_COUPON_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
                membershipCouponNoResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
