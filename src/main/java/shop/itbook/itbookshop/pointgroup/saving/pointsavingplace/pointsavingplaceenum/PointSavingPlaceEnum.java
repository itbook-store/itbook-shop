package shop.itbook.itbookshop.pointgroup.saving.pointsavingplace.pointsavingplaceenum;

/**
 * 상태정보를 조회하거나 사용할때 타입안정성을 유지하기 위한 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public enum PointSavingPlaceEnum {

    ORDER("주문"), ORDER_CANCLE("주문취소"), GIFT("선물"), COUPON("쿠폰");

    private String statusName;

    PointSavingPlaceEnum(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
