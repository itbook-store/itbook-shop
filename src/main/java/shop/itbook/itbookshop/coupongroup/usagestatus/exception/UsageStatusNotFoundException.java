package shop.itbook.itbookshop.coupongroup.usagestatus.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class UsageStatusNotFoundException extends RuntimeException {
    public static final String MESSAGE = "쿠폰 사용 상태가 존재하지 않습니다.";

    public UsageStatusNotFoundException() {
        super(MESSAGE);
    }
}
