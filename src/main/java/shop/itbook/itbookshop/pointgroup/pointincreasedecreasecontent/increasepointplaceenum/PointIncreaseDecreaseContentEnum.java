package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum;

import lombok.Getter;

/**
 * 상태정보를 조회하거나 사용할때 타입안정성을 유지하기 위한 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum PointIncreaseDecreaseContentEnum {

    GIFT("선물"),
    ORDER("주문"),
    ORDER_CANCEL("주문취소"),
    GRADE("등급"),
    COUPON("쿠폰"),
    REVIEW("리뷰");

    private final String statusName;

    PointIncreaseDecreaseContentEnum(String statusName) {
        this.statusName = statusName;
    }

    public static PointIncreaseDecreaseContentEnum stringToEnum(String content) {

        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum = null;

        for (PointIncreaseDecreaseContentEnum value : PointIncreaseDecreaseContentEnum.values()) {
            if (value.getStatusName().equals(content)) {
                pointIncreaseDecreaseContentEnum = value;
                break;
            }
        }

        return pointIncreaseDecreaseContentEnum;
    }
}
