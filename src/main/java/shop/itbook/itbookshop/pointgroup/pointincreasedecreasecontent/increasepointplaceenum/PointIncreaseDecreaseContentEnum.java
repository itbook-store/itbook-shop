package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum;

import lombok.Getter;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;

/**
 * 상태정보를 조회하거나 사용할때 타입안정성을 유지하기 위한 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum PointIncreaseDecreaseContentEnum {

    // 선물적립 선물차감 주문적립 주문차감 주문취소적립 등급적립 쿠폰적립 리뷰적립
    GIFT_INCREASE("선물적립"),
    GIFT_DECREASE("선물차감"),
    ORDER_INCREASE("주문적립"),
    ORDER_DECREASE("주문차감"),
    ORDER_CANCEL_INCREASE("주문취소적립"),
    GRADE_INCREASE("등급적립"),
    COUPON_INCREASE("쿠폰적립"),
    REVIEW_INCREASE("리뷰적립");

    private final String statusName;

    PointIncreaseDecreaseContentEnum(String statusName) {
        this.statusName = statusName;
    }

    public static PointIncreaseDecreaseContentEnum stringToEnum(String dbValue) {

        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum = null;

        for (PointIncreaseDecreaseContentEnum value : PointIncreaseDecreaseContentEnum.values()) {
            if (value.getStatusName().equals(dbValue)) {
                pointIncreaseDecreaseContentEnum = value;
                break;
            }
        }

        return pointIncreaseDecreaseContentEnum;
    }
}
