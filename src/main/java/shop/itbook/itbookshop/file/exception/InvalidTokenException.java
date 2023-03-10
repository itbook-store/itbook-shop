package shop.itbook.itbookshop.file.exception;

/**
 * 토큰이 유효하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String result) {
        super(result);
    }
}
