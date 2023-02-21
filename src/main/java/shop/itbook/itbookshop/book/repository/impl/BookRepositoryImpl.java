package shop.itbook.itbookshop.book.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.book.repository.BookRepositoryCustom;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * BookRepositoryCustom을 구현한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {
    public BookRepositoryImpl() {
        super(Book.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDetailsResponseDto> findBookList() {
        QBook qBook = QBook.book;
        QProduct qProduct = QProduct.product;

        JPQLQuery<BookDetailsResponseDto> bookList =
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(BookDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                    qProduct.isSubscription,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qProduct.isPointApplying,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook, qBook.ebookUrl,
                    qBook.publisherName, qBook.authorName,
                    qProduct.isPointApplyingBasedSellingPrice));
        return bookList.fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookDetailsResponseDto> findBook(Long productNo) {
        QBook qBook = QBook.book;
        QProduct qProduct = QProduct.product;

        JPQLQuery<BookDetailsResponseDto> book =
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(BookDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                    qProduct.isSubscription,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qProduct.isPointApplying,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook, qBook.ebookUrl,
                    qBook.publisherName, qBook.authorName,
                    qProduct.isPointApplyingBasedSellingPrice))
                .where(qProduct.productNo.eq(productNo));
        return Optional.ofNullable(book.fetchOne());
    }

}
