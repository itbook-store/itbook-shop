package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class MyPointHistoryDetailsNotFoundException extends RuntimeException {

    public static final String MESSAGE = "요청한 포인트 내역과 회원정보가 일치하지 않거나 존재하지 않는 포인트내역입니다.";

    public MyPointHistoryDetailsNotFoundException() {
        super(MESSAGE);
    }
}
