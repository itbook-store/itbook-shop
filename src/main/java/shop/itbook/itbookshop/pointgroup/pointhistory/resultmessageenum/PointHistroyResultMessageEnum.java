package shop.itbook.itbookshop.pointgroup.pointhistory.resultmessageenum;

import lombok.Getter;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum PointHistroyResultMessageEnum {
    POINT_HISTORY_LIST_GET_SUCCESS("포인트 리스트 반환에 성공했습니다."),
    MY_POINT_HISTORY_LIST_GET_SUCCESS("특정 멤버 포인트 리스트 반환에 성공했습니다."),
    POINT_HISTORY_DETAILS_GET_SUCCESS("포인트내역의 상세조회가 완료되었습니다.");

    private String resultMessage;

    PointHistroyResultMessageEnum(String resultMessage) {
        this.resultMessage = resultMessage;
    }


}
