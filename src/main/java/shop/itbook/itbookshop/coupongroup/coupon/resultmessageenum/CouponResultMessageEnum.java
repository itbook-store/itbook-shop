package shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum;

import lombok.Getter;

/**
 * 쿠폰의 결과 메세지를 담은 enum 클레스입니다.
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum CouponResultMessageEnum {

    COUPON_SAVE_SUCCESS_MESSAGE("쿠폰 생성에 성공했습니다."),
    COUPON_LIST_SUCCESS_MESSAGE("쿠폰 리스트를 불러오는데 성공했습니다.");
    private String successMessage;

    CouponResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
