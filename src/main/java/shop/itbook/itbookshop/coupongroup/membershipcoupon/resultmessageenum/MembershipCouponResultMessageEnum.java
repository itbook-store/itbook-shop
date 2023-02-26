package shop.itbook.itbookshop.coupongroup.membershipcoupon.resultmessageenum;

import lombok.Getter;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum MembershipCouponResultMessageEnum {
    MEMBERSHIP_COUPON_SAVE_SUCCESS_MESSAGE("등급 쿠폰 생성에 성공했습니다."),
    MEMBERSHIP_COUPON_LIST_SUCCESS_MESSAGE("등급 쿠폰 목록을 불러오는데 성공했습니다."),
    MEMBERSHIP_COUPON_DOWNLOAD_SUCCESS_MESSAGE("등급 쿠폰 발급에 성공했습니다.");

    private final String successMessage;

    MembershipCouponResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
