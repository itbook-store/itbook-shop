package shop.itbook.itbookshop.coupongroup.couponissue.resultmessageenum;

import lombok.Getter;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum CouponIssueResultMessageEnum {
    COUPON_ISSUE_LIST_SUCCESS_MESSAGE("모든 결과 반환에 성공하였습니다."),
    POINT_COUPON_USE_SUCCESS_MESSAGE("포인트 쿠폰 사용에 성공하였습니다.");

    private String successMessage;

    CouponIssueResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
