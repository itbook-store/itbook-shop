package shop.itbook.itbookshop.pointgroup.increase.increasepointplace.increasepointplaceenum;

import lombok.Getter;

/**
 * 상태정보를 조회하거나 사용할때 타입안정성을 유지하기 위한 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum IncreasePointPlaceEnum {

    ORDER("주문"), ORDER_CANCEL("주문취소"), GIFT("선물"), COUPON("쿠폰");

    private final String statusName;

    IncreasePointPlaceEnum(String statusName) {
        this.statusName = statusName;
    }
}
