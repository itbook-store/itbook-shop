package shop.itbook.itbookshop.coupongroup.productcoupon.resultmessageenum;

import lombok.Getter;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum ProductCouponResultMessageEnum {
    PRODUCT_COUPON_SAVE_SUCCESS_MESSAGE("상품 쿠폰 생성에 성공했습니다."),
    PRODUCT_COUPON_LIST_SUCCESS_MESSAGE("상품 쿠폰 리스트를 불러오는데 성공했습니다.");
    private String successMessage;

    ProductCouponResultMessageEnum(String successMessage) {
        this.successMessage = successMessage;
    }
}
