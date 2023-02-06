package shop.itbook.itbookshop.coupongroup.coupontype.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponTypeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "쿠폰 타입이 존재하지 않습니다.";

    public CouponTypeNotFoundException() {
        super(MESSAGE);
    }
}
