package shop.itbook.itbookshop.book.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
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
    public List<FindBookResponseDto> findBookList() {
        QBook qBook = QBook.book;
        QProduct qProduct = QProduct.product;

        JPQLQuery<FindBookResponseDto> bookList =
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(FindBookResponseDto.class,
                    qProduct.productNo, qProduct.name,
                    qProduct.simpleDescription, qProduct.detailsDescription, qProduct.isSelled,
                    qProduct.isDeleted, qProduct.stock, qProduct.increasePointPercent,
                    qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn,
                    qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook, qBook.ebookUrl,
                    qBook.publisherName, qBook.authorName));
        return bookList.fetch();
    }

    @Override
    public FindBookResponseDto findBook(Long id) {
        QBook qBook = QBook.book;
        QProduct qProduct = QProduct.product;

        JPQLQuery<FindBookResponseDto> book =
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(FindBookResponseDto.class,
                    qProduct.productNo, qProduct.name,
                    qProduct.simpleDescription, qProduct.detailsDescription, qProduct.isSelled,
                    qProduct.isDeleted, qProduct.stock, qProduct.increasePointPercent,
                    qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn,
                    qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook, qBook.ebookUrl,
                    qBook.publisherName, qBook.authorName))
                .where(qProduct.productNo.eq(id));
        return book.fetchOne();
    }
}
