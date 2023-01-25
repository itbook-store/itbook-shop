package shop.itbook.itbookshop.productgroup.product.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.book.repository.BookRepositoryCustom;
import shop.itbook.itbookshop.productgroup.product.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepositoryCustom;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductRepositoryImpl extends QuerydslRepositorySupport
    implements ProductRepositoryCustom {
    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public List<FindProductResponseDto> findProductList() {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<FindProductResponseDto> productList =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(FindProductResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isDeleted,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName));
        return productList.fetch();
    }

    @Override
    public FindProductResponseDto findProduct(Long productNo) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<FindProductResponseDto> product =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(FindProductResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isDeleted,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qProduct.productNo.eq(productNo));
        return product.fetchOne();
    }
}
