package shop.itbook.itbookshop.book;

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
            .productNo(null)
            .isbn("isbn")
            .pageCount(10)
            .bookCreatedAt(LocalDateTime.now())
            .isEbook(false)
            .ebookUrl(null)
            .publisherName("출판사")
            .authorName("작가")
            .build();
    }

    public static Book getBookFailure() {

        return Book.builder()
            .productNo(null)
            .isbn(null)
            .pageCount(10)
            .bookCreatedAt(LocalDateTime.now())
            .isEbook(false)
            .ebookUrl(null)
            .publisherName("출판사")
            .authorName("작가")
            .build();
    }
}
