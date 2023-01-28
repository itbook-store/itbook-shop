package shop.itbook.itbookshop.productgroup.product.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepositoryCustom;

/**
 * ProductRepositoryCustom을 구현한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductRepositoryImpl extends QuerydslRepositorySupport
    implements ProductRepositoryCustom {
    public ProductRepositoryImpl() {
        super(Product.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDetailsResponseDto> findProductList() {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productList =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName));
        return productList.fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductDetailsResponseDto> findProductDetails(Long productNo) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> product =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qProduct.productNo.eq(productNo));
        return Optional.ofNullable(product.fetchOne());
    }
}