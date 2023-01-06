package shop.itbook.itbookshop.pointgroup.decrease.decreasepointplace.pointusageplaceenum;


/**
 * 상태정보를 조회하거나 사용할때 타입안정성을 유지하기 위한 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public enum PointDecreasePlaceEnum {

    ORDER("주문"), GIFT("선물");

    private final String statusName;

    PointDecreasePlaceEnum(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
