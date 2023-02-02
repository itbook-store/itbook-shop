package shop.itbook.itbookshop.coupongroup.couponissue.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponIssueNotFoundException extends RuntimeException {
    private static final String MESSAGE = "쿠폰 발급 이력을 찾을 수 없습니다.";

    public CouponIssueNotFoundException() {
        super(MESSAGE);
    }
}
