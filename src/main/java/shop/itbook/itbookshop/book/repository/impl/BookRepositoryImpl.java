package shop.itbook.itbookshop.book.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.dto.response.FindBookListResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.book.repository.BookRepositoryCustom;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * @author 이하늬
 * @since 1.0
 */
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {
    public BookRepositoryImpl() {
        super(Book.class);
    }

    @Override
    public List<FindBookListResponseDto> findBookList() {
        QBook book = QBook.book;
        QProduct product = QProduct.product;

        JPQLQuery<FindBookListResponseDto> bookList =
            from(book)
                .innerJoin(book.product, product)
                .select(Projections.constructor(FindBookListResponseDto.class,
                    product.productNo, product.name,
                    product.simpleDescription, product.detailsDescription, product.isSelled,
                    product.isDeleted, product.stock, product.increasePointPercent,
                    product.rawPrice,
                    product.fixedPrice, product.discountPercent, product.thumbnailUrl, book.isbn,
                    book.pageCount, book.bookCreatedAt, book.isEbook, book.ebookUrl,
                    book.publisherName, book.authorName));
        return bookList.fetch();
    }
}
