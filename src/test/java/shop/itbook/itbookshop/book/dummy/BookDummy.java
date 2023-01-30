package shop.itbook.itbookshop.book.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 이하늬
 * @since 1.0
 */
public class BookDummy {

    public static Book getBookSuccess() {

        return Book.builder()
            .isbn("0192390184")
            .bookCreatedAt(LocalDateTime.now())
            .productNo(1L)
            .publisherName("출판사")
            .authorName("작가")
            .pageCount(10)
            .ebookUrl("testUrl")
            .build();
    }

    public static Book getBookFailure() {

        return Book.builder()
            .isbn(null)
            .bookCreatedAt(LocalDateTime.now())
            .productNo(1L)
            .publisherName("출판사")
            .authorName("작가")
            .pageCount(10)
            .ebookUrl("testUrl")
            .build();
    }
}
