package shop.itbook.itbookshop.book.exception;

/**
 * 도서가 존재하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class BookNotFoundException extends RuntimeException {

    public static final String MESSAGE = "존재하지 않는 도서입니다.";

    public BookNotFoundException() {
        super(MESSAGE);
    }
}
