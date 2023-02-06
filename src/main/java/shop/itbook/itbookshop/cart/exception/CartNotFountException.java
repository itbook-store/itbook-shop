package shop.itbook.itbookshop.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 장바구니에서 해당 상품을 찾지 못할 경우의 예외 클래스입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartNotFountException extends RuntimeException {

    private static final String MESSAGE = "장바구니에서 해당 상품을 찾을 수 없습니다.";

    public CartNotFountException() {
        super(MESSAGE);
    }
}
